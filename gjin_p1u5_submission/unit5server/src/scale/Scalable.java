package scale;


public interface Scalable {
	public  void updateOptionName(String modelName,String optionSetName, String optionName, String newName);
	public void updateOptionPrice(String modelName, String optionSetName,
			String optionName, float newPrice);

}
