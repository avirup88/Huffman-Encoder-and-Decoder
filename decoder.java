import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class decoder {

	public static void main(String args[]){
		final String BINARY_FILENAME = args[0];
		final String DECODED_FILENAME = "decoded.txt";
		final String CODETABLE_FILENAME = args[1];

		//Read code table and create hash table				
		freq_elem rootnode = new freq_elem(-1,0);	//root node
		freq_elem curnode = null;
		
		try {
			File file = new File(CODETABLE_FILENAME);
			
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);			
			String line;
			
			while ((line = bufferedReader.readLine()) != null) {
				curnode = rootnode;
				
				if(line.trim().equals("")){
					continue;
				}
				
				String[] linesplit = line.split(" ");
				
				String code = linesplit[1];
				String symbol = linesplit[0];
				
				for(int i=0; i<code.length(); i++){
					char c = code.charAt(i);
					if(c=='0'){
						if(curnode.getLeftchild()==null){
							freq_elem newnode;
							if(i==code.length()-1){	//leaf node
								newnode = new freq_elem(Integer.parseInt(symbol),0);
							}
							else{
								newnode = new freq_elem(-1,0);
							}
							
							curnode.setLeftchild(newnode);
							curnode = newnode;
						}
						else{
							curnode = curnode.getLeftchild();
						}
					}
					else{		//got 1
						if(curnode.getRightchild()==null){
							freq_elem newnode;
							if(i==code.length()-1){	//leaf node
								newnode = new freq_elem(Integer.parseInt(symbol),0);
							}
							else{
								newnode = new freq_elem(-1,0);
							}
							
							curnode.setRightchild(newnode);
							curnode = newnode;
						}
						else{
							curnode = curnode.getRightchild();
						}
					}
				}
				
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}			
		
		FileInputStream fis =null;
		byte[] bytesArray =null;
		try {
			File file = new File(BINARY_FILENAME);
			
			//init array with file length
			bytesArray = new byte[(int) file.length()];

			fis = new FileInputStream(file);
			fis.read(bytesArray); //read file into bytes[]
			fis.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		StringBuilder compressedcode = new StringBuilder("");
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fw = new FileWriter(DECODED_FILENAME);	
			bw = new BufferedWriter(fw);
			//fw.write("");
			
			
			for (byte b : bytesArray) {
				compressedcode.append(Integer.toBinaryString(b & 255 | 256).substring(1));					
				curnode = rootnode;
				int codelen = compressedcode.length();
				int deleteupto=-1;
				for(int i=0; i<codelen;i++){
					if(compressedcode.charAt(i)=='0'){
						curnode=curnode.getLeftchild();
						if(curnode.getLeftchild()==null && curnode.getRightchild()==null){	//leaf node
							bw.write(curnode.getEntry()+"\n");
							deleteupto=i;
							curnode=rootnode;
						}
					}
					else{
						curnode=curnode.getRightchild();
						if(curnode.getLeftchild()==null && curnode.getRightchild()==null){	//leaf node
							bw.write(curnode.getEntry()+"\n");
							deleteupto=i;
							curnode=rootnode;
						}
					}
					
				}
				compressedcode.delete(0, deleteupto+1);
			}
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				bw.flush();
				fw.flush();
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
		
	}
	

