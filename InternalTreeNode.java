
public class InternalTreeNode extends TreeNode{
	
	
	
	public InternalTreeNode()
	{
		
	}
	
	public InternalTreeNode(TreeNode item1,TreeNode item2)
	
	{
		if(item1.value<item2.value)
        {
        	this.left = item1;
        	this.right = item2;
        	
        }
        else
        {
        	this.left = item2;
        	this.right = item1;
        }
	
	
	this.value = item1.value + item2.value;
	root = this;
	
	}
	
	

}
