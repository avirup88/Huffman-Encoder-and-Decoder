import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Vector;

public class BuildFreqTable {
	
	private BufferedReader br;
	public Vector<String> input = new Vector<String>();
	public LinkedHashMap<String, Integer> freq_map = new LinkedHashMap<String, Integer>();
	
	public  BuildFreqTable(BufferedReader b) throws IOException, NumberFormatException
	
	{
		br = b;
		generate_freq_table();
		
	}
	
	private void generate_freq_table() 
	{
		String sCurrentLine;
		
		try {
			while ((sCurrentLine = br.readLine()) != null) {
				
				if(sCurrentLine.trim().equals(""))
				{
					continue;
				}
				else if(Integer.parseInt(sCurrentLine.trim()) < 0 || Integer.parseInt(sCurrentLine.trim()) > 999999)
				{
					throw new NumberFormatException();
				}
				else
				{
					input.add(sCurrentLine.trim());
				}
				
				if (freq_map.containsKey(sCurrentLine.trim()))
					{
						
					freq_map.put(sCurrentLine.trim(),freq_map.get(sCurrentLine.trim()) + 1);
					}
				else
				{
					freq_map.put(sCurrentLine.trim(),1);
				}
			}
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Invalid Input : The input should be integers between 0 to 999999");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	LinkedHashMap<String, Integer> get_map()
	{
		return freq_map;
	}
	


}
