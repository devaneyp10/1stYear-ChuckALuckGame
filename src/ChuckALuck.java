import java.util.Scanner;

public class ChuckALuck {
	public static final int TRIPLE_ODDS = 30;

	public static void main(String[] args) {

		System.out.print("Welcome to the Chuck a Luck game! \n\n");
		Wallet wallet = new Wallet();
		Scanner input = new Scanner(System.in);
		System.out.println("How much money would you like to put into your wallet?");
		Double cash = input.nextDouble();
		wallet.put(cash);
		String betType = null;

		boolean finished = false;
		while (finished == false && wallet.check() != 0 && betType != "quit") {
			Scanner typeScanner = new Scanner(System.in);
			System.out.print("Enter the type of bet you want to place: ");
			betType = typeScanner.nextLine();
			resolveBet(betType, wallet);
			if (wallet.check() == 0 || betType == "quit") {
				finished = false;
			}

		}
		if (finished == false) {
			betSummary(cash, wallet);

		}

	}

	public static void resolveBet(String betType, Wallet wallet) {
		System.out.println("You now have €" + wallet.check() + ", how much would you like to place on this bet?");
		Scanner betScanner = new Scanner(System.in);
		double stake = betScanner.nextDouble();
		if (stake <= wallet.check()) {

			Dice dice1 = new Dice();
			Dice dice2 = new Dice();
			Dice dice3 = new Dice();

			int result1 = dice1.roll();
			int result2 = dice2.roll();
			int result3 = dice3.roll();
			int totalResult = result1 + result2 + result3;

			switch (betType) {
			case "triple":
				if (result1 == result2 && result1 == result3 && result2 == result3 && result1 != 1 && result1 != 6) {
					wallet.put(stake * TRIPLE_ODDS);
					System.out.println("You win! €" + stake * TRIPLE_ODDS + " has been put into your wallet.\n");
				} else {
					wallet.get(stake);
					System.out.println("Unlucky you lose! €" + stake + " has been taken out of your account.\n");
				}
				break;
			case "field":
				if (totalResult < 8 || totalResult > 12) {
					wallet.put(stake);
					System.out.println("You win! €" + stake + " has been put into your wallet.\n");
				} else {
					wallet.get(stake);
					System.out.println("Unlucky you lose! €" + stake + " has been taken out of your account.\n");
				}
				break;
			case "high":
				if (totalResult > 10 && result1 != result2 && result1 != result3 && result2 != result3) {
					wallet.put(stake);
					System.out.println("You win! €" + stake + " has been put into your wallet.\n");
				} else {
					wallet.get(stake);
					System.out.println("Unlucky you lose! €" + stake + " has been taken out of your account.\n");
				}
				break;
			case "low":
				if (totalResult < 11 && result1 != result2 && result1 != result3 && result2 != result3) {
					wallet.put(stake);
					System.out.println("You win! €" + stake + " has been put into your wallet.\n");
				} else {
					wallet.get(stake);
					System.out.println("Unlucky you lose! €" + stake + " has been taken out of your account.\n");
				}
				break;
			default:
				System.out.println("Sorry, this is not a valid bet type.\n");
			}
		} else
			System.out.println("Sorry, you dont have enough money in your wallet to make that bet.\n");

	}

	public static void betSummary(double initialCash, Wallet wallet) {
		if (wallet.check() == 0) {
			System.out.println("\nHard luck, you have run out of money! You started off with €" + initialCash
					+ " and ended with nothing. Better luck next time!");
		}
		if (wallet.check() > initialCash) {
			System.out.print("\nWell done, you came out ahead! You started off with €" + initialCash
					+ " and ended up with €" + wallet.check() + " which means you're leaving with a difference of €"
					+ (wallet.check() - initialCash) + ".");
		}
		if (wallet.check() < initialCash && wallet.check() != 0) {
			System.out.print("\nHard luck, you came out behind!You started off with €" + initialCash
					+ " and ended up with €" + wallet.check() + " which means you're leaving with a difference of €"
					+ (wallet.check() - initialCash) + ".");
		}
	}
}
