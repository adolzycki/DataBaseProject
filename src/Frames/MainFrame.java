package Frames;

import javax.swing.*;

public class MainFrame extends JFrame {
    private JTextField selectField;
    private JTextField fromField;
    private JTextField whereField;
    private JButton querryButton;
    private JList functionList;
    private JTextField firstParameterFieldText;
    private JTextField secondParameterFieldText;
    private JButton executeProcedureButton;
    private JPanel mainPanel;
    private JTextField groupByTextField;
    private JTextField thirdParameterTextField;
    private JTextField insertIntoTextField;
    private JTextField deleteTextField;
    private JTextField updateTextField;
    private JTextField valueTextField;
    private JTextField deleteWhereTextField;
    private JTextField updateWhereTextField;
    private JButton insertButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField setTextField;
    private JLabel InsertIntoLabel;
    private JLabel valueLabel;
    private JLabel deleteFromLabel;
    private JLabel deleteWhereLabel;
    private JLabel updateWhereLabel;
    private JLabel setLabel;
    private JLabel updateLabel;
    private JScrollPane scroll;
    private JButton usagesButton;
    private JButton exportButton;
    private JButton importButton;
    private DefaultListModel<String> functionsListModel;

    public MainFrame(String user) {
        super("Main Frame");
        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        addFunctionsForUser();
        if(!user.equals("admin")) {
            hidePart3();
            if(!user.equals("nauczyciel")) {
                hidePart2();
            }
        }
    }

    private void hidePart2() {
        firstParameterFieldText.setVisible(false);
        secondParameterFieldText.setVisible(false);
        thirdParameterTextField.setVisible(false);
        executeProcedureButton.setVisible(false);
        functionList.setVisible(false);
        scroll.setVisible(false);
        usagesButton.setVisible(false);

    }


    private void hidePart3() {
        InsertIntoLabel.setVisible(false);
        valueLabel.setVisible(false);
        deleteFromLabel.setVisible(false);
        deleteWhereLabel.setVisible(false);
        updateWhereLabel.setVisible(false);
        setLabel.setVisible(false);
        updateLabel.setVisible(false);

        updateButton.setVisible(false);
        insertButton.setVisible(false);
        deleteButton.setVisible(false);
        importButton.setVisible(false);
        exportButton.setVisible(false);

        valueTextField.setVisible(false);
        deleteWhereTextField.setVisible(false);
        deleteTextField.setVisible(false);
        insertIntoTextField.setVisible(false);
        updateTextField.setVisible(false);
        setTextField.setVisible(false);
        updateWhereTextField.setVisible(false);
    }

    public JButton getQuerryButton() {
        return querryButton;
    }

    public JTextField getFromField() {
        return fromField;
    }

    public JTextField getSelectField() {
        return selectField;
    }

    public JTextField getWhereField() {
        return whereField;
    }

    public JTextField getGroupByTextField() {
        return groupByTextField;
    }

    public JButton getExportButton() {
        return exportButton;
    }

    public JButton getImportButton() {
        return importButton;
    }

    public JButton getExecuteProcedureButton() {
        return executeProcedureButton;
    }

    public JList getFunctionList() {
        return functionList;
    }

    private void createUIComponents() {
        functionsListModel = new DefaultListModel<>();
        functionList = new JList(functionsListModel);
        functionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        functionList.setLayoutOrientation(JList.VERTICAL);
        functionList.setVisibleRowCount(-1);
    }

    public JTextField getFirstParameterFieldText() {
        return firstParameterFieldText;
    }

    public JTextField getSecondParameterFieldText() {
        return secondParameterFieldText;
    }

    public JTextField getThirdParameterTextField() {
        return thirdParameterTextField;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getInsertButton() {
        return insertButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JTextField getDeleteTextField() {
        return deleteTextField;
    }

    public JTextField getDeleteWhereTextField() {
        return deleteWhereTextField;
    }

    public JTextField getUpdateTextField() {
        return updateTextField;
    }

    public JTextField getUpdateWhereTextField() {
        return updateWhereTextField;
    }

    public JTextField getInsertIntoTextField() {
        return insertIntoTextField;
    }

    public JTextField getValueTextField() {
        return valueTextField;
    }

    public JButton getUsagesButton() {
        return usagesButton;
    }

    public JTextField getSetTextField() {
        return setTextField;
    }

    private void addFunctionsForUser() {
        functionsListModel.clear();
        functionsListModel.addElement("funkcjaNaOcenach");
        functionsListModel.addElement("poprawaOceny");
        functionsListModel.addElement("przeniesienieUcznia");
        functionsListModel.addElement("usprawiedliwienie");
        functionsListModel.addElement("zmianaNauczycielaPrzedmiotu");
        functionsListModel.addElement("zmianaRokuNauki");
        functionsListModel.addElement("zmianaTytuluNauczyciela");
        functionsListModel.addElement("zmianaWychowawcyKlasy");
        functionsListModel.addElement("zwolnienieNauczyciela");
        functionsListModel.addElement("uczniowieWKlasie");
        functionsListModel.addElement("ocenyTestu");

        functionList.setSelectedIndex(0);
        functionList.ensureIndexIsVisible(0);
    }
}
