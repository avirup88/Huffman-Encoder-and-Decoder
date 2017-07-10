import java.util.Vector;
	
public class min4wayheap {

	    private Vector<TreeNode> list;

	    public min4wayheap() {

	        this.list = new Vector<TreeNode>();
	    }

	    public min4wayheap(Vector<TreeNode> items) {

	        this.list = items;
	        buildHeap();
	    }

	    public void insert(TreeNode item) {

	        list.add(item);
	        int i = list.size() - 1;
	        int parent = parent(i);

	        while (parent != i && list.get(i).value < list.get(parent).value && parent>=3) {

	            swap(i, parent);
	            i = parent;
	            parent = parent(i);
	        }
	        
	    }

	    public void buildHeap() {
	    	int start_pos = ((list.size()-4) / 4) +3;

	        for (int i = start_pos; i >= 3; i--) {
	            minHeapify(i);
	        }
	        
	        
	    }

	    public TreeNode extractMin() {

	        if (list.size() == 3) {

	            throw new IllegalStateException("MinHeap is EMPTY");
	        } else if (list.size() == 4) {

	        	TreeNode min = list.remove(3);
	            return min;
	        }

	        // remove the last item ,and set it as new root
	        TreeNode min = list.get(3);
	        TreeNode lastItem = list.remove(list.size() - 1);
	        list.set(3, lastItem);

	        // bubble-down until heap property is maintained
	        minHeapify(3);

	        return min;
	    }


	    private void minHeapify(int i) {
	    	
	    	
	    	if (i<3)
	    	{
	    		return;
	    	}

	        int left = left(i);
	        int right = right(i);
	        int mid1 = mid1(i);
	        int mid2 = mid2(i);
	        int smallest = -1;

	        // find the smallest key between current node and its children.
	        if (left <= list.size() - 1 && list.get(left).value < list.get(i).value) {
	            smallest = left;
	        } else {
	            smallest = i;
	        }
	        
	        if (mid1 <= list.size() - 1 && list.get(mid1).value < list.get(smallest).value) {
	            smallest = mid1;
	        }
	        
	        if (mid2 <= list.size() - 1 && list.get(mid2).value < list.get(smallest).value) {
	            smallest = mid2;
	        }
	        
	        if (right <= list.size() - 1 && list.get(right).value < list.get(smallest).value) {
	            smallest = right;
	        }

	        // if the smallest key is not the current key then bubble-down it.
	        if (smallest != i) {

	            swap(i, smallest);
	            minHeapify(smallest);
	        }
	    }

	    public TreeNode getMin() {

	        return list.get(3);
	    }

	    public boolean isEmpty() {

	        return list.size() == 3;
	    }

	    private int right(int i) {

	        return 4 * i - 5;
	    }

	    private int left(int i) {

	        return 4 * i - 8;
	    }
	    
	    private int mid1(int i) {

	        return 4 * i - 6;
	    }
	    
	    private int mid2(int i) {

	        return 4 * i - 7;
	    }
	    

	    private int parent(int i) {

	        if ((i-3) % 4 == 1) {
	            return ((i-3) / 4)+3;
	        }

	        return ((i - 4) / 4)+3;
	    }

	    private void swap(int i, int parent) {

	    	TreeNode temp = list.get(parent);
	        list.set(parent, list.get(i));
	        list.set(i, temp);
	    }
	    
	    public int heapsize()
	    {
	    	return list.size();
	    }

	}

