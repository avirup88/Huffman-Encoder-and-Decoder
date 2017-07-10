import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

public class encoder {

	Vector<String> key = new Vector<String>();
	Vector<String> encoded_val = new Vector<String>();
	StringBuilder codetable = new StringBuilder();
	LinkedHashMap<String,String> encode_map = new LinkedHashMap<String,String>();

	void Traversal(TreeNode root,StringBuilder s) throws IOException
	{


		if (root!=null)
		{
			if(root instanceof TerminalTreeNode)
			{

				encode_map.put(((TerminalTreeNode) root).key, s.toString());
				codetable.append((((TerminalTreeNode)root).key + " "+ s+"\n").toString());

			}


			if(root.left!=null)
			{
				Traversal(root.left,s.append("0"));
				s.setLength(s.length()-1);
			}

			if(root.right!=null)
			{
				Traversal(root.right,s.append("1"));
				s.setLength(s.length()-1);
			}
		}

	}

	

	public static void main(String[] args)
	{
		BufferedReader br = null;
		FileReader fr = null;
		Vector<TreeNode> fte = new Vector<TreeNode>();
		String FILENAME = args[0];
		try {


				br = new BufferedReader(new FileReader(FILENAME));

				BuildFreqTable freq_obj = new BuildFreqTable(br);
				LinkedHashMap<String,Integer> local_map = freq_obj.get_map();

				fte.add(new TerminalTreeNode("",0));
				fte.add(new TerminalTreeNode("",0));
				fte.add(new TerminalTreeNode("",0));
				
				for (Map.Entry<String, Integer> entry : local_map.entrySet()) {
					fte.add(new TerminalTreeNode(entry.getKey(),entry.getValue()));
				}


				min4wayheap heap = new min4wayheap(fte);

				InternalTreeNode it = new InternalTreeNode();
				
				

				while (heap.heapsize() >= 5)
				{
					TreeNode t1 = heap.extractMin();
					TreeNode t2 = heap.extractMin();
					it = new InternalTreeNode(t1,t2);
					heap.insert(it);

				}

				File fout = new File("code_table.txt");
				FileOutputStream fos = new FileOutputStream(fout);
				OutputStreamWriter osw = new OutputStreamWriter(fos);



				encoder temp = new encoder();	
				
				
				if (local_map.size()==1)
				{
					Map.Entry<String,Integer> entry=local_map.entrySet().iterator().next();
					String key= entry.getKey();
					temp.codetable.append(key +" "+"0"+"\n").toString();
					temp.encode_map.put(key, "0");
				}
				else
				{
				temp.Traversal(it.root, new StringBuilder());
				}
							
				
				
				osw.write(temp.codetable.toString());
				osw.close();

				StringBuilder encoded_string = new StringBuilder();

				for(String key : freq_obj.input)	
				{

					encoded_string.append(temp.encode_map.get(key));

				}

				try
				{

					File fout1 = new File("encoded.bin");
					FileOutputStream fos1 = new FileOutputStream(fout1);


					//Converting the string to bits

					if (encoded_string.length()>=8)
					{
						int pos =0;
						int count = 0;
						while(pos < (encoded_string.length()-7))
						{
							count++;
							byte nextByte = 0x00;
							for(int i=0;i<8 && pos+i < encoded_string.length();i++)
							{
								nextByte = (byte) (nextByte <<1);
								nextByte += encoded_string.charAt(pos+i) == '0'?0x0:0x1;
							}

							fos1.write(nextByte);
							pos+=8;

						}

						encoded_string.delete(0, 8*count);
					}


					fos1.close();

				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			
		
		} catch (NumberFormatException e) {

			System.out.println(e.getMessage());
		}

		
		catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {

					if (br != null)
						br.close();

					if (fr != null)
						fr.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}

			}
		}


	}
