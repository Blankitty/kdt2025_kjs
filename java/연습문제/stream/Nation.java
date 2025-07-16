package streamproject.exam01;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import streamproject.exam01.Nation;

public class NationApp extends JFrame {
    private final JButton btnSort;
    private JTable jTable;
    JComboBox<String> cmbOrder;
    DefaultTableModel tableModel;

    NationApp() {
        setTitle("국가");

        add(new JScrollPane(makeTable()), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        add(panel, BorderLayout.SOUTH);

        String[] cmbString = {"국가명", "인구수", "GDP"};
        cmbOrder = new JComboBox<>(cmbString);
        panel.add(cmbOrder);

        btnSort = new JButton("정렬");
        btnSort.addActionListener(this::sortTable);  // 정렬 기능 연결
        panel.add(btnSort);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setVisible(true);
    }

    JTable makeTable() {
        if (jTable == null) {
            jTable = new JTable();
            tableModel = new DefaultTableModel();  // 테이블 모델 저장
            jTable.setModel(tableModel);

            tableModel.addColumn("번호");
            tableModel.addColumn("국가명");
            tableModel.addColumn("유형");
            tableModel.addColumn("인구수");
            tableModel.addColumn("GDP순위");

            jTable.getColumn("번호").setPreferredWidth(30);
            jTable.getColumn("국가명").setPreferredWidth(100);
            jTable.getColumn("유형").setPreferredWidth(30);
            jTable.getColumn("인구수").setPreferredWidth(30);
            jTable.getColumn("GDP순위").setPreferredWidth(30);

            CenterTableCellRenderer ctcr = new CenterTableCellRenderer();
            for (int i = 0; i < jTable.getColumnCount(); i++) {
                jTable.getColumnModel().getColumn(i).setCellRenderer(ctcr);
            }

            // 초기 Nation 데이터 삽입
            populateTable(Nation.nations);
        }
        return jTable;
    }

    // 테이블에 데이터 채우기
    private void populateTable(List<Nation> nationList) {
        tableModel.setRowCount(0); // 기존 행 삭제
        int no = 1;
        for (Nation nation : nationList) {
            Object[] row = {
                no++,
                nation.getName(),
                nation.getType().toString(),
                nation.getPopulation(),
                nation.getGdpRank()
            };
            tableModel.addRow(row);
        }
    }

    // 정렬 동작 구현
    private void sortTable(ActionEvent e) {
        String selected = (String) cmbOrder.getSelectedItem();
        List<Nation> sortedList = new ArrayList<>(Nation.nations); // 원본 보호

        switch (selected) {
            case "국가명":
                sortedList.sort(Comparator.comparing(Nation::getName));
                break;
            case "인구수":
                sortedList.sort(Comparator.comparingDouble(Nation::getPopulation).reversed());
                break;
            case "GDP":
                sortedList.sort(Comparator.comparingInt(Nation::getGdpRank));
                break;
        }

        populateTable(sortedList);
    }

    // 셀 가운데 정렬
    public static class CenterTableCellRenderer extends JLabel implements TableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setFont(new Font(null, Font.PLAIN, 12));
            setHorizontalAlignment(JLabel.CENTER);
            setOpaque(true);
            setBackground(isSelected ? Color.YELLOW : Color.WHITE);
            return this;
        }
    }

    public static void main(String[] args) {
        new NationApp();
    }
}

