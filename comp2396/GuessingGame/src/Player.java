
public class Player {
	String name;
	int number;
	public void guess() {
		number = (int)(Math.random()*10);
		System.out.println(this.name + " guessed " + number);
	}
	Player(String name){
		this.name = name;
	}

}
