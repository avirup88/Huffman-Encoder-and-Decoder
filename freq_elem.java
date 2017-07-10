
public class freq_elem {
	private int entry;
	private int freq;
	private freq_elem leftchild;
	private freq_elem rightchild;
	
	public freq_elem(int entry, int freq){
		this.entry=entry;
		this.freq=freq;
		this.leftchild=null;
		this.rightchild=null;
	}
	
	public freq_elem getLeftchild() {
		return leftchild;
	}

	public void setLeftchild(freq_elem leftchild) {
		this.leftchild = leftchild;
	}

	public freq_elem getRightchild() {
		return rightchild;
	}

	public void setRightchild(freq_elem rightchild) {
		this.rightchild = rightchild;
	}

	public int getEntry() {
		return entry;
	}

	public void setEntry(int entry) {
		this.entry = entry;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}
	
	@Override
	public String toString(){
		return (entry+":"+freq+"::left="+leftchild+"...right="+rightchild);
	}
	
}
