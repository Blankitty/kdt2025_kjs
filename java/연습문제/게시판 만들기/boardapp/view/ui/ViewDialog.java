package boardapp.view.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import boardapp.model.dto.Board;

public class ViewDialog extends JDialog {
    private BoardMain boardApp;
    private Board board;

    private JPanel pCenter, pTitle, pWriter, pContent, pSouth;
    private JTextField txtTitle, txtWriter;
    private JTextArea txtContent;
    private JButton btnEdit, btnDelete, btnClose;

    public ViewDialog(BoardMain boardApp, Board board) {
        super(boardApp);
        this.boardApp = boardApp;
        this.board = board;

        setTitle("게시물 보기");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(false);
        setSize(400, 270);
        setLocationRelativeTo(boardApp);

        getContentPane().add(getPCenter(), BorderLayout.CENTER);
        getContentPane().add(getPSouth(), BorderLayout.SOUTH);

        txtTitle.setText(board.getTitle());
        txtWriter.setText(board.getWriter());
        txtContent.setText(board.getContent());

        txtTitle.setEditable(false);
        txtWriter.setEditable(false);
        txtContent.setEditable(false);
    }

    private JPanel getPCenter() {
        if (pCenter == null) {
            pCenter = new JPanel();
            pCenter.setBorder(new EmptyBorder(10, 10, 10, 10));
            pCenter.add(getPTitle());
            pCenter.add(getPWriter());
            pCenter.add(getPContent());
        }
        return pCenter;
    }

    public JPanel getPTitle() {
        if (pTitle == null) {
            pTitle = new JPanel(new BorderLayout());
            JLabel label = new JLabel("제목");
            label.setPreferredSize(new Dimension(70, 30));
            label.setHorizontalAlignment(JLabel.CENTER);
            txtTitle = new JTextField();
            txtTitle.setPreferredSize(new Dimension(300, 30));
            pTitle.add(label, BorderLayout.WEST);
            pTitle.add(txtTitle, BorderLayout.CENTER);
        }
        return pTitle;
    }

    public JPanel getPWriter() {
        if (pWriter == null) {
            pWriter = new JPanel(new BorderLayout());
            JLabel label = new JLabel("글쓴이");
            label.setPreferredSize(new Dimension(70, 30));
            label.setHorizontalAlignment(JLabel.CENTER);
            txtWriter = new JTextField();
            txtWriter.setPreferredSize(new Dimension(300, 30));
            pWriter.add(label, BorderLayout.WEST);
            pWriter.add(txtWriter, BorderLayout.CENTER);
        }
        return pWriter;
    }

    public JPanel getPContent() {
        if (pContent == null) {
            pContent = new JPanel(new BorderLayout());
            JLabel label = new JLabel("내용");
            label.setPreferredSize(new Dimension(70, 30));
            label.setHorizontalAlignment(JLabel.CENTER);
            txtContent = new JTextArea();
            txtContent.setPreferredSize(new Dimension(300, 100));
            txtContent.setBorder(new EtchedBorder());
            pContent.add(label, BorderLayout.WEST);
            pContent.add(txtContent, BorderLayout.CENTER);
        }
        return pContent;
    }

    public JPanel getPSouth() {
        if (pSouth == null) {
            pSouth = new JPanel();
            pSouth.setBackground(Color.WHITE);
            pSouth.add(getBtnEdit());
            pSouth.add(getBtnDelete());
            pSouth.add(getBtnClose());
        }
        return pSouth;
    }

    private JButton getBtnEdit() {
        if (btnEdit == null) {
            btnEdit = new JButton("수정");
            btnEdit.addActionListener(e -> enableEdit());
        }
        return btnEdit;
    }

    private JButton getBtnDelete() {
        if (btnDelete == null) {
            btnDelete = new JButton("삭제");
            btnDelete.addActionListener(e -> {
                int result = JOptionPane.showConfirmDialog(this,
                        "정말로 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    boardApp.deleteBoard(board);
                    dispose();
                }
            });
        }
        return btnDelete;
    }

    private JButton getBtnClose() {
        if (btnClose == null) {
            btnClose = new JButton("닫기");
            btnClose.addActionListener(e -> dispose());
        }
        return btnClose;
    }

    private void enableEdit() {
        txtTitle.setEditable(true);
        txtWriter.setEditable(true);
        txtContent.setEditable(true);
        btnEdit.setText("저장");

        for (ActionListener al : btnEdit.getActionListeners()) {
            btnEdit.removeActionListener(al);
        }

        btnEdit.addActionListener(e -> {
            board.setTitle(txtTitle.getText().trim());
            board.setWriter(txtWriter.getText().trim());
            board.setContent(txtContent.getText().trim());
            boardApp.updateBoard(board);
            dispose();
        });
    }
}
