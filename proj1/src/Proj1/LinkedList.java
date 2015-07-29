package Proj1;

/**
 * @name LinkedList
 * @author Rachael Birky
 * @Section 01
 * @date 02.20.2014
 *
 * @description A class representing a list of items
 * 				represented as nodes and linked with pointers
 * @param <T> Holds Objects of Any Type
 */
public class LinkedList<T> implements Iterable<T>{
	
	private Node<T> head;
	private int size = 0;
	
	/**
	 * @name Node
	 * @description An inner class of the LinkedList class
	 * 				That stores an Object of Any Type
	 * 				and a pointer to the next item (child)
	 * 				in the Linked List 
	 *
	 */

	public class Node<T>{
		private T item;
		private Node<T> child;
		
		/**
		 * @name Node
		 * @description Constructor method.  This
		 * 				method creates a new Node
		 * 				with an object of any type
		 * 				and a child Node
		 * @param aItem
		 * @param aChild
		 * @return none
		 */
		public Node(T aItem, Node<T> aChild){
			this.item = aItem;
			this.child = aChild;
		}
		
		/**
		 * @name getItem
		 * @description Returns the item of the current Node
		 * @param none
		 * @return this.item: returns the data stored in this Node
		 */
		public T getItem(){
			return this.item;
		}
	}
	
	/**
	 * @name Node
	 * @description An inner class of the LinkedList class
	 * 				That creates an iterator object, used to acces each
	 * 				Node in the list in order
	 *
	 */
	private class LinkedListIterator implements java.util.Iterator<T>{
		private Node<T> current;
		private Node<T> prev = null;

		
		public LinkedListIterator(Node<T> aNode){
			this.current = aNode;
		}
		
		/**
		 * @name hasNext
		 * @description Overrides Iterator hasNext
		 * 				returns true if the current node has a child,
		 * 				false if the current node's child is null
		 * @param none
		 * @return true or false, whether the current Node has a child node
		 */
		@Override
		public boolean hasNext() {
			return current != null;
		}

		/**
		 * @name next
		 * @description Overrides Iterator next()
		 * @param none
		 * 
		 * @return	nextItem: the item that the current node holds
		 * 			or null if the current node has no child 
		 */
		@Override
		public T next() {
			if (!hasNext()){
				return null;
			}
			else{
				T nextItem = current.getItem();
				current = current.child;
				return nextItem;
			}
		}

		/**
		 * @name remove
		 * @description Overrides Iterator remove()
		 * 				If the current and previous Nodes are null,
		 * 				point the head to the child of current
		 * 				Otherwise remove current by point from the
		 * 				previous Node to the child of current
		 *
		 * @param none
		 * @return	none
		 */
		@Override
		public void remove() {
			if (current!=null){
				if (prev != null){
					prev.child = current.child;
				} else {
					head = current.child;
				}
			}			
		}
	}
		

	/**
	 * @name LinkedList
	 * @description Constructor method.  This
	 * 				method creates a new LinkedList
	 * 				object with a null head Node
	 * @param none
	 * @return none
	 */
	public LinkedList(){
		this.head = null;
	}
	
	/**
	 * @name addNode
	 * @description Adds the given item as a new
	 * 				Node to the end of the Linked
	 * 				List
	 * @param T aItem: the object to be added, of
	 * 				any type
	 * @return none
	 */
	public void addNode(T aItem){
		
		/*Add Node as Head, if it is the first Node
		 * being added (head is currently null)
		 */
		if (head == null){
			head = new Node<T>(aItem, null);
		}
		
		/*Iterate until the last Node (a node without 
		 * a child) is found. Add the new item as the
		 * last Node (child)
		 */
		else{
			Node<T> temp = head;
			
			while (temp.child != null){
				temp = temp.child;
			}
			
			temp.child = new Node<T>(aItem, null);
		}
		this.size++;
	}
	
	/**
	 * @name getNode
	 * @description Removes and returns the nodes in 
	 * 				order, beginning with the head
	 * @param none
	 * @return The next node in the LinkedList
	 * 				that is not null
	 */
	public T getNode(){
		T returnItem = this.head.getItem();
		this.head = this.head.child;
		size--;
		return returnItem;
		
	}
	
	/**
	 * @name length
	 * @description Returns the length of the
	 * 				LinkedList
	 * @param none
	 * @return this.count, the total number of
	 * 				nodes in the LinkedList
	 */
	public int length(){
		return this.size;
	}
	
	/**
	 * @name iterator
	 * @description Overrides Iterator iterator
	 * 				returns a new iterator object
	 * 				for the current LinkedList
	 * @param none
	 * @return new LinkedListIterator(this.head):
	 * 				a new iterator with the current LinkedList's
	 * 				head as the starting Node
	 */
	@Override
	public java.util.Iterator<T> iterator(){
		return new LinkedListIterator(this.head);
	}
	
	
	/* Unit testing
	public static void main(String[] args){
		LinkedList<Integer> aList = new LinkedList<Integer>();
		Integer i = new Integer(1);
		Integer j = new Integer(2);
		aList.addNode(i);
		aList.addNode(j);
		System.out.println(aList.getNode());
		for (int k=0; k<aList.length(); k++){
			System.out.println(aList.getNode());
		}
	}*/

}