import Exceptions.MissingFromParameterException;
import Exceptions.MissingSelectParameterException;
import Exceptions.WrongParametersException;
import Exceptions.WrongTableExceptions;
import Frames.LoginFrame;
import Frames.MainFrame;
import com.mysql.jdbc.MysqlDataTruncation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLException;


public class Connector {

    LoginFrame loginFrame;
    MainFrame mainFrame;
    JDBC jdbc;

    Connector() {
        loginFrameInit();

    }

    private void loginFrameInit() {
        loginFrame = new LoginFrame();

        loginFrame.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    connect(loginFrame.getLoginTextField().getText(), loginFrame.getPasswordTextField().getText());
                    loginFrame.hideWindow();
                    mainFrameInit(loginFrame.getLoginTextField().getText());
                }
                catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Wrong login or password","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    private void connect(String login,String password) throws SQLException {
        jdbc = JDBC.connect(login,password);
    }

    private void mainFrameInit(String string) {
        mainFrame = new MainFrame(string);

        mainFrame.getQuerryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    jdbc.createQuerry(mainFrame.getSelectField().getText(), mainFrame.getFromField().getText(), mainFrame.getWhereField().getText(),mainFrame.getGroupByTextField().getText());
                }
                catch (WrongTableExceptions | MissingSelectParameterException | MissingFromParameterException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        mainFrame.getExecuteProcedureButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                try {
                    jdbc.createCall(mainFrame.getFunctionList().getSelectedValue().toString(), mainFrame.getFirstParameterFieldText().getText(), mainFrame.getSecondParameterFieldText().getText(), mainFrame.getThirdParameterTextField().getText());
                } catch (WrongParametersException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        mainFrame.getInsertButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jdbc.insert(mainFrame.getInsertIntoTextField().getText(),mainFrame.getValueTextField().getText());
            }
        });

        mainFrame.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jdbc.delete(mainFrame.getDeleteTextField().getText(),mainFrame.getDeleteWhereTextField().getText());
            }
        });

        mainFrame.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jdbc.update(mainFrame.getUpdateTextField().getText(),mainFrame.getSetTextField().getText(),mainFrame.getUpdateWhereTextField().getText());
            }
        });

        mainFrame.getUsagesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String str =  "funkcjaNaOcnach (’<testID>’, ’<funcja_agg>’)\n" +
                        "ocenyTestu(’<testID>’)\n" +
                        "poprawaOceny (’<testID>’, ’<uczen>’, ’<nowaOcena>’)\n" +
                        "przeniesienieUcznia (’<uczen>’, ’<nowaKlasa>’)\n" +
                        "ucznioweWKlasie(’<klasa>’)\n" +
                        "usprawiedliwienie (’<uczen>’, ’<data_nieobecnosci>’)\n" +
                        "zmianaNauczycielaPrzedmiotu (’<przedmiot>’, ’<nowyNauczyciel>’)\n" +
                        "zmianaRokuNauki (’<klasa>’, ’<rok_nauki>’)\n" +
                        "zmianaTytuluNauczyciela (’<nauczyciel>’, ’<nowyTytul>’)\n" +
                        "zmianaWychowawcyKlasy (’<klasal>’, ’<nowyWychowawcal>’)\n" +
                        "zwolnienieNauczyciela (’<nauczyciel>’)\n" +
                        "\n" +
                        "informacje_klasy\n" +
                        "informacje_klasy_km\n" +
                        "przedmioty_ilosc_nauczycieli\n" +
                        "roknauki_ilosc_uczniow\n" +
                        "uczniowie_najwiecej_nieobecnosci";
                JTextArea info = new JTextArea(str);
                JOptionPane.showMessageDialog(null,info,"Info",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        mainFrame.getExportButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FileWriter fw = null;

                String path="/home/daniquani/Documents/SemestrIII/BD/Lab/Lab4/szkola_projektBackup.sql";
                String dumpCommand = "mysqldump -u mike --password=mike123 --events --routines szkola_projekt -r " +path;
                // Runtime.getRuntime().exec("mysqldump -u root --password= gestiondestock > "+path);
                File data = new File(path);


                try{
                    fw = new FileWriter(data);
                    fw.close();
                }catch(IOException ex){
                    ex.printStackTrace();
                }


                Runtime rt = Runtime.getRuntime();

                try {
                    Process proc = rt.exec(dumpCommand);
                    InputStream in = proc.getInputStream();
                    InputStreamReader read = new InputStreamReader(in,"latin1");
                    BufferedReader br = new BufferedReader(read);
                    BufferedWriter bw = new BufferedWriter(new FileWriter(data,true));
                    String line=null;
                    StringBuffer buffer = new StringBuffer();

                    while    ((line=br.readLine())!=null){
                        buffer.append(line+"\n");
                    }
                    String toWrite = buffer.toString();
                    bw.write(toWrite);
                    bw.close();
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mainFrame.getImportButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String path = "/home/daniquani/Documents/SemestrIII/BD/Lab/Lab4/szkola_projektBackup.sql";
                String[] pat = {"mysql", "-u", "mike", "--pasword=mike123", "szkola_projekt", "<", "/home/daniquani/Documents/SemestrIII/BD/Lab/Lab4/szkola_projektBackup.sql"};

                Process runtimeProcess;
                try {
                    runtimeProcess = Runtime.getRuntime().exec(pat);
                    int processComplete = runtimeProcess.waitFor();
                    if (processComplete == 0) {
                        System.out.println("Backup restored successfully");

                    } else {
                        System.out.println("Could not restore the backup");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        });



    }
}
