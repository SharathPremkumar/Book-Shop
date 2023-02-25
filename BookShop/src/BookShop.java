import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BookShop {

	protected JFrame BookShop;
	private JTextField txtbookname;
	private JTextField txtedition;
	private JTextField txtprice;
	
	private JTable table;
	private JTextField txtbookid;
	private JTextField txtqty;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookShop window = new BookShop();
					window.BookShop.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	

	/**
	 * Create the application.
	 */
	public BookShop() {
		initialize();
		Connect();
		tableLoad();
	}
	
	private String url = "jdbc:mysql://localhost:3306/bookshop";
	private String user = "root";
	private String password = "root";
	private Connection con;
	private PreparedStatement pst;
	private ResultSet res;

//	private JTextField textQty;


	public void Connect() 
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded");
			con = DriverManager.getConnection(url, user, password);
			System.out.println("Connection Established");
			
		}
		catch(ClassNotFoundException e) {
			
		}
		catch(SQLException e) {
			
		}
	}
	
	public void tableLoad() {
		try {
			pst = con.prepareStatement("select * from book");
			res = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(res));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		BookShop = new JFrame();
		BookShop.getContentPane().setBackground(new Color(240, 240, 240));
		BookShop.getContentPane().setForeground(new Color(0, 0, 0));
		BookShop.setBounds(100, 100, 728, 494);
		BookShop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BookShop.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setBounds(297, 11, 132, 41);
		BookShop.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 63, 296, 211);
		BookShop.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 34, 85, 17);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(10, 78, 85, 17);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Price");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1.setBounds(10, 124, 85, 17);
		panel.add(lblNewLabel_1_1_1);
		
		txtbookname = new JTextField();
		txtbookname.setBounds(120, 34, 157, 20);
		panel.add(txtbookname);
		txtbookname.setColumns(10);
		
		txtedition = new JTextField();
		txtedition.setColumns(10);
		txtedition.setBounds(120, 75, 157, 20);
		panel.add(txtedition);
		
		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(120, 124, 157, 20);
		panel.add(txtprice);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Qty");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1_1.setBounds(10, 170, 85, 17);
		panel.add(lblNewLabel_1_1_1_1);
		
		txtqty = new JTextField();
		txtqty.setColumns(10);
		txtqty.setBounds(120, 170, 157, 20);
		panel.add(txtqty);
		

		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				String bookname, edition, price, qty;
				
				bookname = txtbookname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();
				qty = txtqty.getText();
				
				try {
					pst = con.prepareStatement("insert into book(bookName,edition,price,qty)values(?,?,?,?)");
					pst.setString(1, bookname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.setString(4, qty);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(txtbookname, "Record Added");;
		
					txtbookname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtqty.setText("");
					txtbookname.requestFocus();
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(new Color(128, 255, 128));
		btnNewButton.setBounds(20, 285, 83, 41);
		BookShop.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setForeground(Color.BLACK);
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnExit.setBackground(new Color(128, 255, 128));
		btnExit.setBounds(113, 285, 83, 41);
		BookShop.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtbookname.setText("");
				txtedition.setText("");
				txtprice.setText("");
				txtqty.setText("");
				txtbookname.requestFocus();
				
			}
		});
		btnClear.setForeground(Color.BLACK);
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnClear.setBackground(new Color(128, 255, 128));
		btnClear.setBounds(206, 285, 83, 41);
		BookShop.getContentPane().add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(322, 63, 365, 291);
		BookShop.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Serach", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 355, 296, 70);
		BookShop.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		txtbookid = new JTextField();
		txtbookid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					
					String id = txtbookid.getText();
					
						pst = con.prepareStatement("select bookName,edition,price,qty from book where id = ?");
						pst.setString(1, id);
						res = pst.executeQuery();
						
					if(res.next()== true) 
					{
						
						String name = res.getString(1);
						String edition = res.getString(2);
						String price = res.getString(3);
						String qty = res.getString(4);
						
						txtbookname.setText(name);
						txtedition.setText(edition);
						txtprice.setText(price);
						txtqty.setText(qty);
					}
					else
					{
						txtbookname.setText("");
						txtedition.setText("");
						txtprice.setText("");
						txtqty.setText("");
					}
				}
				catch(Exception e1) {
					
				}
				
				
			}
		});
		txtbookid.setColumns(10);
		txtbookid.setBounds(115, 26, 157, 20);
		panel_1.add(txtbookid);
		
		JLabel lblNewLabel_1_2 = new JLabel("Book ID");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(10, 26, 85, 17);
		panel_1.add(lblNewLabel_1_2);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bookname, edition, price, bid, qty;
				
				bookname = txtbookname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();
				qty = txtqty.getText();
				bid = txtbookid.getText();
				
				try {
					pst = con.prepareStatement("update book set bookname = ?, edition = ?, price = ? , qty = ? where id = ?");
					pst.setString(1, bookname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.setString(4, qty);
					pst.setString(5, bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(txtbookname, "Record Updated");;
	
					txtbookname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtqty.setText("");
					txtbookname.requestFocus();
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setForeground(Color.BLACK);
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnUpdate.setBackground(new Color(128, 255, 128));
		btnUpdate.setBounds(410, 384, 83, 41);
		BookShop.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bid;
				
				bid = txtbookid.getText();
				
				try {
					pst = con.prepareStatement("delete from book where id = ?");
					pst.setString(1, bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(txtbookname, "Record Deleted");;
			//		table_load();
					txtbookname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbookname.requestFocus();
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setForeground(Color.BLACK);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnDelete.setBackground(new Color(128, 255, 128));
		btnDelete.setBounds(527, 384, 83, 41);
		BookShop.getContentPane().add(btnDelete);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage logout = new LoginPage();
				logout.Login.setVisible(true);
				BookShop.dispose();
			}
		});
		btnLogout.setForeground(Color.BLACK);
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnLogout.setBackground(new Color(128, 255, 128));
		btnLogout.setBounds(604, 11, 83, 41);
		BookShop.getContentPane().add(btnLogout);
	}
}
