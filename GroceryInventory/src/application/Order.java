package application;

public class Order {
	private int ID;
	private String ProductName;
	private String ProductBarcode;
	private int QuantitySold;
	private double TotalPrice;
	private Vendor Vendor;
	
	public Order(String productName, String productBarcode, int quantitySold, double totalPrice) {
		this.ProductName = productName;
		this.ProductBarcode = productBarcode;
		this.QuantitySold = quantitySold;
		this.TotalPrice = totalPrice;
	}
	
	public Order(int iD, String productName, String productBarcode, int quantitySold, double totalPrice) {
		this.ID = iD;
		this.ProductName = productName;
		this.ProductBarcode = productBarcode;
		this.QuantitySold = quantitySold;
		this.TotalPrice = totalPrice;
	}
	
	public Order(int iD, String productName, String productBarcode, int quantitySold, double totalPrice, Vendor vendor) {
		this.ID = iD;
		this.ProductName = productName;
		this.ProductBarcode = productBarcode;
		this.QuantitySold = quantitySold;
		this.TotalPrice = totalPrice;
		this.Vendor = vendor;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public int getQuantitySold() {
		return QuantitySold;
	}
	public void setQuantitySold(int quantitySold) {
		QuantitySold = quantitySold;
	}
	public double getTotalPrice() {
		return TotalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		TotalPrice = totalPrice;
	}
	public Vendor getVendor() {
		return Vendor;
	}
	public void setVendor(Vendor vendor) {
		Vendor = vendor;
	}

	public String getProductBarcode() {
		return ProductBarcode;
	}

	public void setProductBarcode(String productBarcode) {
		ProductBarcode = productBarcode;
	}

}
