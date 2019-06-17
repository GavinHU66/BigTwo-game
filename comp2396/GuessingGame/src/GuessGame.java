
public class GuessGame {
	
	public void startGame() {
		Player p1 = new Player("Ann");
		Player p2 = new Player("Ben");
		
		int targetNumber = (int)(Math.random()*10);
		System.out.println("Target number is " + targetNumber);
		
		boolean isFinished = false;
		while(!isFinished){
			p1.guess();
			p2.guess();
			if (p1.number == targetNumber) {
				isFinished = true;
				System.out.println(p1.name + "got it right!");
			}
			if (p2.number == targetNumber) {
				isFinished = true;
				System.out.println(p2.name + " got it right!");
			}
		}
	}
}
