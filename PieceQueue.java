public class PieceQueue {
	public static final int NPREVIEWS=4;
	private Node first,
	last;
	private static class Node{
		private static Randomizer br;
		private Mino piece;
		public Node next;
		public Node(){
			this.piece=br.getNext();
		}
		public Mino getPiece(){
			return this.piece;
		}
		public static void setBR(Mino[] pieces){
			br=new BagRandomizer(pieces);
		}
	}
	public PieceQueue(Mino[] pieces, Block[][] field, int[] rows){
		Node.setBR(pieces);
		this.first=new Node();
		Node temp=first;
		for(int i=0;i<PieceQueue.NPREVIEWS;i++){
			this.last=new Node();
			temp.next=last;
			temp=this.last;
		}
	}
	public Mino getNext(){
		Mino temp=this.first.getPiece();
		Node newNode=new Node();
		this.first=this.first.next;
		this.last.next=newNode;
		this.last=newNode;
		return temp;
	}
	public Mino[] visualize(){
		Mino[] preview=new Piece[PieceQueue.NPREVIEWS];
		Node temporal=this.first;
		for(int i=0; i<preview.length; i++){
			preview[i]=temporal.getPiece();
			temporal=temporal.next;
		}
		return preview;
	}
}

