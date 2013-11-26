package org.chinasb.example.qzonelogin;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.http.client.methods.HttpGet;
import org.chinasb.login.component.JPopupTextArea;
import org.chinasb.login.model.PTUI;
import org.chinasb.login.util.HttpUtils;
import org.chinasb.login.util.MD5Utils;
import org.chinasb.login.util.QQUtils;
import org.chinasb.login.util.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * 登陆主界面
 * @author zhujuan
 *
 */
public class Main extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    private JLabel jLabel_UID;
    private JTextField jTextField_UID;
    private JLabel jLabel_PWD;
    private JPasswordField jTextField_PWD;
    private JLabel jLabel_VerifyCode;
    private JTextField jTextField_VerifyCode;
    private JCheckBox jCheckBox;
    private JButton jButton_Login;
    private static JPopupTextArea jTextArea;
    private JScrollPane jScrollPane;
    
    public Main() {
        super();
        this.setSize(800, 600);
        this.getContentPane().setLayout(null);
        this.add(getJLabelUID(), null);
        this.add(getJTextFieldUID(), null);
        this.add(getJLabelPWD(), null);
        this.add(getJTextFieldPWD(), null);
        this.add(getjLabelVerifyCode(), null);
        this.add(getJTextFieldVerifyCode(), null);
        this.add(getJCheckBox(), null);
        this.add(getJButtonLogin(), null);
        this.add(getJScrollPane(), null);
        this.setTitle("QQ空间登陆器");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    
    private JLabel getJLabelUID() {
        if (jLabel_UID == null) {
            jLabel_UID = new JLabel();
            jLabel_UID.setBounds(265, 20, 60, 30);
            jLabel_UID.setText("QQ号码：");
        }
        return jLabel_UID;
    }
    
    private JTextField getJTextFieldUID() {
        if (jTextField_UID == null) {
            jTextField_UID = new JTextField();
            jTextField_UID.setBounds(325, 20, 150, 30);
        }
        return jTextField_UID;
    }
    
    private JLabel getJLabelPWD() {
        if (jLabel_PWD == null) {
            jLabel_PWD = new JLabel();
            jLabel_PWD.setBounds(485, 20, 60, 30);
            jLabel_PWD.setText("QQ密码：");
        }
        return jLabel_PWD;
    }
    
    private JPasswordField getJTextFieldPWD() {
        if (jTextField_PWD == null) {
            jTextField_PWD = new JPasswordField();
            jTextField_PWD.setBounds(545, 20, 150, 30);
        }
        return jTextField_PWD;
    }
    
    private JLabel getjLabelVerifyCode() {
        if (jLabel_VerifyCode == null) {
            jLabel_VerifyCode = new JLabel("点击刷新验证码");
            jLabel_VerifyCode.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel_VerifyCode.setBounds(15, 10, 130, 50);
            jLabel_VerifyCode.setFont(new Font("Serif", Font.BOLD, 14));
            jLabel_VerifyCode.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            jLabel_VerifyCode.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent event) {
                    refreshVerifyCode();
                }
            });
        }
        return jLabel_VerifyCode;
    }
    
    private void refreshVerifyCode() {
        try {
            HttpGet httpGet = new HttpGet("http://captcha.qq.com/getimage?uin=" + jTextField_UID.getText() + "&aid=549000912&" + Math.random());
            appendJTextArea("Http Request：" + httpGet.getURI());
            jLabel_VerifyCode.setIcon(new ImageIcon(HttpUtils.request(httpGet)));
            jLabel_VerifyCode.setText("");
            jCheckBox.setSelected(true);
            jTextField_VerifyCode.setText("");
            jTextField_VerifyCode.setVisible(true);
        } catch (Exception e) {
            appendJTextArea(e.getMessage());
        }
    }
    private JTextField getJTextFieldVerifyCode() {
        if (jTextField_VerifyCode == null) {
            jTextField_VerifyCode = new JTextField();
            jTextField_VerifyCode.setBounds(180, 20, 70, 30);
            jTextField_VerifyCode.setVisible(false);
        }
        return jTextField_VerifyCode;
    }
    
    private JCheckBox getJCheckBox() {
        if (jCheckBox == null) {
            jCheckBox = new JCheckBox();
            jCheckBox.setBounds(150, 20, 30, 30);
            jCheckBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (jCheckBox.isSelected()) {
                        refreshVerifyCode();
                    } else {
                        jLabel_VerifyCode.setText("点击刷新验证码");
                        jLabel_VerifyCode.setIcon(null);
                        jTextField_VerifyCode.setVisible(false);
                    }
                }
            });
        }
        return jCheckBox;
    }
    
    private JButton getJButtonLogin() {
        if (jButton_Login == null) {
            jButton_Login = new JButton();
            jButton_Login.setBounds(715, 20, 60, 30);
            jButton_Login.setText("登陆");
            jButton_Login.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    try {
                        // 第一步获取login_sig
                        HttpGet httpGet = new HttpGet("http://ui.ptlogin2.qq.com/cgi-bin/login?daid=5&pt_qzone_sig=1&hide_title_bar=1&low_login=0&qlogin_auto_login=1&no_verifyimg=1&link_target=blank&appid=549000912&style=12&target=self&s_url=http%3A//qzs.qq.com/qzone/v5/loginsucc.html?para=izone&pt_qr_app=%CA%D6%BB%FAQQ%BF%D5%BC%E4&pt_qr_link=http%3A//z.qzone.com/download.html&self_regurl=http%3A//qzs.qq.com/qzone/v6/reg/index.html&pt_qr_help_link=http%3A//z.qzone.com/download.html");
                        appendJTextArea("Http Request：" + httpGet.getURI());                        
                        String responseBody = new String(HttpUtils.request(httpGet));
                        appendJTextArea("Http Response: " + responseBody);
                        PTUI ptui = JSON.parseObject(StringUtils.findPattern("pt.ptui\\=\\{(.+)}", responseBody).replace("pt.ptui=", "").replace("encodeURIComponent(", "").replace(")", ""), PTUI.class);
                        if(ptui == null) {
                            JOptionPane.showMessageDialog(null, "请求登陆签名失败");
                            return;
                        }
                        // 第二步获取验证码
                        String verifyCode = null;
                        String uin = null;
                        if (jCheckBox.isSelected()) {
                            if (jTextField_VerifyCode.getText().equals("")) {
                                JOptionPane.showMessageDialog(null, "请输入验证码");
                                jTextField_VerifyCode.requestFocusInWindow();
                                return;
                            }
                            if (jTextField_VerifyCode.getText().length() > 4) {
                                JOptionPane.showMessageDialog(null, "非法验证码");
                                jTextField_VerifyCode.requestFocusInWindow();
                                return;
                            }
                            verifyCode = jTextField_VerifyCode.getText().trim();
                        } else {
                            httpGet = new HttpGet("http://check.ptlogin2.qq.com/check?regmaster=&uin=" + jTextField_UID.getText() + "&appid=549000912&js_ver=10052&js_type=1&login_sig=" + ptui.getLogin_sig() + "&u1=http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&r=" + Math.random());
                            appendJTextArea("Http Request：" + httpGet.getURI());
                            responseBody = new String(HttpUtils.request(httpGet));
                            appendJTextArea("Http Response: " + responseBody);
                            String[] params = StringUtils.findPattern(QQUtils.FUNCTION_PATTERN, responseBody).replace("'", "").split(",");
                            if (params == null || !params[0].equals("0")) {
                                refreshVerifyCode();
                                JOptionPane.showMessageDialog(null, "请求自动验证失败");
                                jButton_Login.requestFocusInWindow();
                                return;
                            }
                            uin = params[2];
                            verifyCode = params[1];
                        }
                        // 第三步登陆空间
                        httpGet = new HttpGet("http://ptlogin2.qq.com/login?u=" + jTextField_UID.getText() + "&p=" + QQUtils.getPwd(uin == null ? MD5Utils.uin2hex(jTextField_UID.getText()) : uin, new String(jTextField_PWD.getPassword()).trim(), verifyCode) + "&verifycode=" + verifyCode + "&aid=549000912&u1=http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&h=1&ptredirect=0&ptlang=2052&daid=5&from_ui=1&dumy=&low_login_enable=0&regmaster=&fp=loginerroralert&action=2-21-1385452444158&mibao_css=&t=1&g=1&js_ver=10052&js_type=1&login_sig=" + ptui.getLogin_sig() + "&pt_rsa=0&pt_qzone_sig=1");
                        appendJTextArea("Http Request：" + httpGet.getURI());
                        responseBody = new String(HttpUtils.request(httpGet));
                        appendJTextArea("Http Response: " + responseBody);
                        String[] params = StringUtils.findPattern(QQUtils.FUNCTION_PATTERN, responseBody).replace("'", "").split(",");
                        if (params == null || !params[0].equals("0")) {
                            JOptionPane.showMessageDialog(null, "请求登陆失败" + params != null ? ":" + params[4] : "");
                            jButton_Login.requestFocusInWindow();
                            return;
                        }
                        JOptionPane.showMessageDialog(null, params[5] + ", " + params[4]);
                    } catch (Exception e) {
                        appendJTextArea(e.getMessage());
                    }
                }
            });
        }
        return jButton_Login;
    }
    
    private JTextArea getJTextArea() {
        if (jTextArea == null) {
            jTextArea = new JPopupTextArea();
            jTextArea.setEditable(false);
            jTextArea.setLineWrap(true);
            jTextArea.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent mouseEvent) {
                    jTextArea.setCursor(new Cursor(Cursor.TEXT_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    jTextArea.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });
            jTextArea.getCaret().addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    jTextArea.getCaret().setVisible(true);
                }
            });
        }
        return jTextArea;
    }
    
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setBounds(new Rectangle(15, 65, 760, 490));
            jScrollPane.setViewportView(getJTextArea());
        }
        return jScrollPane;
    }
    
    public void appendJTextArea(byte[] bytes) {
        appendJTextArea(new String(bytes));
    }
    
    public void appendJTextArea(String text) {
        int idealSize = 10240;
        int maxExcess = 512;
        int excess = jTextArea.getDocument().getLength() - idealSize;
        if (excess >= maxExcess) {
            jTextArea.replaceRange(" ", 0, excess);
        }
        jTextArea.append(text + "\r\n");
        jTextArea.setCaretPosition(jTextArea.getText().length());
    }
    
    public static void main(String args[]) throws Exception {
        Main main = new Main();
        main.setVisible(true);
    }
}
