package application;

import java.util.Vector;

public class Vendor {

	private int ID;
	private String Name;
	private String Phone;
	private String Email;
	private Vector<Product> ProductsOffered;
	
	public Vendor() {
		
	}

	public Vendor(int ID, String Name) {
		this.ID = ID;
		this.Name = Name;
		this.ProductsOffered = new Vector<Product>();
	}
	public Vendor(int ID, String Name, String Phone, String Email) {
		this.ID = ID;
		this.Name = Name;
		this.setPhone(Phone);
		this.setEmail(Email);
		this.ProductsOffered = new Vector<Product>();
	}
	
	public Vendor(int ID, String Name, String Phone, String Email, Vector<Product> ProductsOffered) {
		this.ID = ID;
		this.Name = Name;
		this.ProductsOffered = ProductsOffered;
	}
	
	public Vendor(int ID, String Name, Vector<Product> ProductsOffered) {
		this.ID = ID;
		this.Name = Name;
		this.ProductsOffered = ProductsOffered;
	}

	public Vector<Product> getProductsOffered() {
		return ProductsOffered;
	}

	public void setProductsOffered(Vector<Product> productsOffered) {
		ProductsOffered = productsOffered;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

}
