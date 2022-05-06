package ssh;

import com.jcraft.jsch.*;
import data.EUsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DeviceConnect {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceConnect.class);
    protected Session session;

    protected String ip;
    protected String user;
    protected String pass;

    protected int port = 22;

    public DeviceConnect(String ip, String user, String pass) {
        this.ip = ip;
        this.user = user;
        this.pass = pass;
    }

    public DeviceConnect(String ip, EUsers user) {
        this.ip = ip;
        this.user = user.getLogin();
        this.pass = user.getPassword();
    }

    /**
     * Отправка указанного файла на сервер.
     * @param src путь к отправляемому файлу
     * @param dts путь к месту назначения
     */
    public void sendFileToServer(String src, String dts) {
        try {
            this.connectSession();
            ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();
            sftp.put(src, dts);
        } catch (Exception err) {
            err.printStackTrace();
        }
        this.disconnectSession();
    }

    public String getLencValue() {
        StringBuilder buffer = new StringBuilder();
        try {
            this.connectSession();
            ChannelExec exec = (ChannelExec) session.openChannel("exec");
            exec.setCommand("echo -e 'PROTOCOL 1\nGETLENSVALUE' | nc 0 4030 -w 1");
            exec.setInputStream(null);
            InputStream in = exec.getInputStream();
            exec.connect();
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            while (true) {
                String str;
                while ((str = buffReader.readLine()) != null) {
                    buffer.append(str).append("\n");
                }
                if (exec.isClosed()) {
                    if (in.available() > 0) {
                        continue;
                    }
                    break;
                }
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        this.disconnectSession();
        //LogMessages.logInfo("Executed");
        return buffer.toString().split("VALUE=")[1].replace("\n", "").replace("\r", "");
    }

    public void sendStringIntoFileOnServer(String src, String dts) {
        try {
            this.connectSession();
            ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();
            sftp.put(new ByteArrayInputStream(src.getBytes(StandardCharsets.UTF_8)), dts);
        } catch (Exception err) {
            err.printStackTrace();
        }
        this.disconnectSession();
    }

    public String getTextFileFromServer(String path, String file) {
        try {
            this.connectSession();
            ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();
            char c = path.charAt(path.length() - 1);
            if (c != '/') {
                path += "/";
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(sftp.get(path + file)));
            StringBuilder buff = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                buff.append(str).append("\n");
            }
            return buff.toString();
        } catch (Exception err) {
            err.printStackTrace();
            return null;
        }
    }

    /**
     * Выполнение команды на сервере.
     */
    public String executeCommand(String command) {
        LOGGER.info("SSH command: '%s'", command);
        StringBuilder buffer = new StringBuilder();
        try {
            this.connectSession();
            ChannelExec exec = (ChannelExec) session.openChannel("exec");
            exec.setCommand(command);
            exec.setInputStream(null);
            InputStream in = exec.getInputStream();
            exec.connect();
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            int loopIter = 100;
            int iter = 0;
            while (true) {
                if (iter >= loopIter){
                    break;
                }
                iter = iter + 1;

                String str;
                while ((str = buffReader.readLine()) != null) {
                    buffer.append(str).append("\n");
                }
                if (exec.isClosed()) {
                    if (in.available() > 0) {
                        continue;
                    }
                    break;
                }
                iter =+ 1;
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        this.disconnectSession();
        //LogMessages.logInfo("Executed");
        return buffer.toString();
    }

    /**
     * Метод, получающий значение из sql.
     * @param variable
     * @return
     */
    public String getValueFromSql(String variable) {
        String[] results = executeCommand(
                String.format(
                        "sqlite3 /tftpboot/boot/conf/kris.sql3 \"select tValue  from tblSettings WHERE tName='%s' \"",
                        variable)
        ).split("\n");
        if (results.length  > 1){
            return "";
        }
        return results[0];
    }

    public void setPort(int port) {
        this.port = port;
    }

    void connectSession() throws JSchException {
        disconnectSession();
        session = new JSch().getSession(user, ip, port);
        session.setPassword(pass);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
    }

    void disconnectSession() {
        if (session != null) {
            session.disconnect();
        }
    }
}
