import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class LoginPage {

	protected JFrame Login;
	private JTextField textUserName;
	private JTextField textPwd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
					window.Login.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginPage() {
		initialize();
		Connect();
	}

	private String url = "jdbc:mysql://localhost:3306/bookshop?autoReconnect=true&useSSL=false";
	private String user = "root";
	private String password = "root";
	private Connection con;
	private PreparedStatement pst;

	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded");
			con = DriverManager.getConnection(url, user, password);
			System.out.println("Connection Established");

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {

		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Login = new JFrame();
		Login.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		Login.setBounds(100, 100, 385, 356);
		Login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Login.getContentPane().setLayout(null);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String usName, pwd;
				ResultSet execute;

				usName = textUserName.getText();
				pwd = textPwd.getText();

				try {
					
					pst = con.prepareStatement("select * from shopowner where username = ? and pwd = ?");
					pst.setString(1, usName);
					pst.setString(2, pwd);
					execute = pst.executeQuery();
				if(execute.next()==true) 
				{
					BookShop bShop = new BookShop();
					bShop.BookShop.setVisible(true);
					Login.dispose();
					JOptionPane.showMessageDialog(textUserName, "Welcome");
				}
				else
				{
					JOptionPane.showMessageDialog(textUserName, "You are not Shopkeeper");
				}
					textUserName.setText("");
					textPwd.setText("");
				} catch (Exception e1) {

				}
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLogin.setBounds(132, 233, 107, 34);
		Login.getContentPane().add(btnLogin);

		JLabel LabelUserName = new JLabel("User Name");
		LabelUserName.setFont(new Font("Times New Roman", Font.BOLD, 14));
		LabelUserName.setBounds(50, 90, 75, 26);
		Login.getContentPane().add(LabelUserName);

		textUserName = new JTextField();
		textUserName.setBounds(139, 94, 172, 20);
		Login.getContentPane().add(textUserName);
		textUserName.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblPassword.setBounds(50, 155, 75, 26);
		Login.getContentPane().add(lblPassword);

		textPwd = new JTextField();
		textPwd.setColumns(10);
		textPwd.setBounds(139, 159, 172, 20);
		Login.getContentPane().add(textPwd);

		JLabel lblNewLabel = new JLabel("Please login Shopkeeper");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblNewLabel.setBounds(76, 28, 252, 34);
		Login.getContentPane().add(lblNewLabel);
	}
}
