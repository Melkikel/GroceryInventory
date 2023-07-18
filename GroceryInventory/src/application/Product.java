package application;

public class Product {

	private int ID;
	private String Name;
	private Category Category;
	private double BuyingPrice;
	private double SellingPrice;
	private Vendor Vendor;
	private int Quantity;
	private String Barcode;

	public Product() {
	}

	public Product(int ID, String Name, Category Category, double BuyingPrice, double SellingPrice, int Quantity) {
		this.ID = ID;
		this.Quantity = 1;
	}

	public Product(int ID, String Name, Category Category, Vendor Vendor, int Quantity) {
		this.ID = ID;
		this.Name = Name;
		this.Vendor = Vendor;
		this.Quantity = Quantity;
	}

	public Product(int ID, String Name, Category Category, double BuyingPrice, double SellingPrice, Vendor Vendor,
			int Quantity, String Barcode) {
		this.ID = ID;
		this.Name = Name;
		this.Vendor = Vendor;
		this.Quantity = Quantity;
		this.Category = Category;
		this.BuyingPrice = BuyingPrice;
		this.SellingPrice = SellingPrice;
		this.Barcode = Barcode;
	}
	
	public Product(String Name, Category Category, double BuyingPrice, double SellingPrice, Vendor Vendor,
			int Quantity, String Barcode) {
		this.Name = Name;
		this.Vendor = Vendor;
		this.Quantity = Quantity;
		this.Category = Category;
		this.BuyingPrice = BuyingPrice;
		this.SellingPrice = SellingPrice;
		this.Barcode = Barcode;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Category getCategory() {
		return Category;
	}

	public void setCategory(Category category) {
		Category = category;
	}

	public double getBuyingPrice() {
		return BuyingPrice;
	}

	public void setBuyingPrice(double buyingPrice) {
		BuyingPrice = buyingPrice;
	}

	public double getSellingPrice() {
		return SellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		SellingPrice = sellingPrice;
	}

	public Vendor getVendor() {
		return Vendor;
	}

	public void setVendor(Vendor vendor) {
		Vendor = vendor;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	
	public String getBarcode() {
		return Barcode;
	}
	
	public void setBarcode(String barcode) {
		Barcode = barcode;
	}
}