package boardapp.view.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

import boardapp.model.dao.BoardDAO;
import boardapp.model.dto.Board;

public class BoardMain extends JFrame {
    private JTable jTable;
    private JPanel pSouth;
    private JButton btnInsert;
    private DefaultTableModel tableModel;

    private List<Board> boardList;
    private BoardDAO dao = new BoardDAO();

    public BoardMain() {
        setTitle("게시판 리스트");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(new JScrollPane(getJTable()), BorderLayout.CENTER);
        getContentPane().add(getPSouth(), BorderLayout.SOUTH);
        setSize(600, 450);
        setVisible(true);
        refreshTable();  // 시작 시 데이터 불러오기
    }

    JTable getJTable() {
        if (jTable == null) {
            tableModel = new DefaultTableModel();
            tableModel.addColumn("번호");
            tableModel.addColumn("제목");
            tableModel.addColumn("글쓴이");
            tableModel.addColumn("날짜");
            tableModel.addColumn("조회수");

            jTable = new JTable(tableModel);
            jTable.getColumn("번호").setPreferredWidth(20);
            jTable.getColumn("제목").setPreferredWidth(250);
            jTable.getColumn("글쓴이").setPreferredWidth(50);
            jTable.getColumn("날짜").setPreferredWidth(50);
            jTable.getColumn("조회수").setPreferredWidth(20);

            CenterTableCellRenderer ctcr = new CenterTableCellRenderer();
            for (int i = 0; i < jTable.getColumnCount(); i++) {
                jTable.getColumnModel().getColumn(i).setCellRenderer(ctcr);
            }

            jTable.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int rowIndex = jTable.getSelectedRow();
                    if (rowIndex != -1) {
                        Board board = boardList.get(rowIndex);
                        dao.increaseViews(board.getId());
                        refreshTable();
                        new ViewDialog(BoardMain.this, board).setVisible(true);
                    }
                }
            });
        }
        return jTable;
    }

    public class CenterTableCellRenderer extends JLabel implements TableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setFont(new Font(null, Font.PLAIN, 12));
            setHorizontalAlignment(JLabel.CENTER);
            setOpaque(true);
            setBackground(isSelected ? Color.YELLOW : Color.WHITE);
            return this;
        }
    }

    public JPanel getPSouth() {
        if (pSouth == null) {
            pSouth = new JPanel();
            pSouth.add(getBtnInsert());
        }
        return pSouth;
    }

    public JButton getBtnInsert() {
        if (btnInsert == null) {
            btnInsert = new JButton("추가");
            btnInsert.addActionListener(e -> new InsertDialog(BoardMain.this).setVisible(true));
        }
        return btnInsert;
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        boardList = dao.getAllBoards();  // DB에서 다시 불러오기
        for (Board b : boardList) {
            tableModel.addRow(new Object[] {
                b.getId(),
                b.getTitle(),
                b.getWriter(),
                b.getDate(),
                b.getViews()
            });
        }
    }

    public void addBoard(Board board) {
        dao.insertBoard(board);   // DB에 저장
        refreshTable();           // 다시 불러오기
    }

    public void deleteBoard(Board board) {
        dao.deleteBoard(board.getId());
        refreshTable();
    }

    public void updateBoard(Board board) {
        dao.updateBoard(board);
        refreshTable();
    }

    public static void main(String[] args) {
        new BoardMain();
    }
}
