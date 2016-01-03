package Data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.QuickLoadPNG;

import java.util.Random;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class Game {

	private Texture background, P1, P2, P3, P4, menu, diceTexture, zoom, popupbox;
	private UI menuUI, popup, roll, jail;
	private int nrPlayers = Rules.getInstance().getNrPlayers(), Order[] = { 1, 2, 3, 4 }, rolltimer = 0, dice1 = 0,
			dice2 = 7, currentPlayer = 1, phase = 1, taxmoney = 0;
	private Player player1, player2, player3, player4;
	private boolean Popup = false, doroll = false, displayDice = false, isdouble = false, clicked = false,
			clicked2 = false, paid = false, auction = false;
	Special Start = new Special(0, 0);
	Property Brown1 = new Property(1, 60, 0, 1, 0, 2, 4, 10, 30, 90, 160, 250);
	Special Chest1 = new Special(2, 4);
	Property Brown2 = new Property(3, 60, 0, 1, 0, 4, 8, 20, 60, 180, 320, 450);
	Special Tax1 = new Special(4, 6);
	Bus Bus1 = new Bus(5, 200, 0, 0);
	Property Teal1 = new Property(6, 100, 0, 1, 0, 6, 12, 30, 90, 270, 400, 550);
	Special Chance1 = new Special(7, 5);
	Property Teal2 = new Property(8, 100, 0, 1, 0, 6, 12, 30, 90, 270, 400, 550);
	Property Teal3 = new Property(9, 120, 0, 1, 0, 8, 16, 40, 100, 300, 450, 600);
	Special Jail = new Special(10, 1);
	Property Pink1 = new Property(11, 140, 0, 2, 0, 10, 20, 50, 150, 450, 625, 750);
	Utility Utility1 = new Utility(12, 150, 0, 0);
	Property Pink2 = new Property(13, 140, 0, 2, 0, 10, 20, 50, 150, 450, 625, 750);
	Property Pink3 = new Property(14, 160, 0, 2, 0, 12, 24, 60, 180, 500, 700, 900);
	Bus Bus2 = new Bus(15, 200, 0, 0);
	Property Orange1 = new Property(16, 180, 0, 2, 0, 14, 28, 70, 200, 550, 750, 950);
	Special Chest2 = new Special(17, 4);
	Property Orange2 = new Property(18, 200, 0, 2, 0, 14, 28, 70, 200, 550, 750, 950);
	Property Orange3 = new Property(19, 200, 0, 2, 0, 16, 32, 80, 220, 600, 800, 1000);
	Special Parking = new Special(20, 2);
	Property Red1 = new Property(21, 220, 0, 3, 0, 18, 36, 90, 250, 700, 875, 1050);
	Special Chance2 = new Special(22, 5);
	Property Red2 = new Property(23, 220, 0, 3, 0, 18, 36, 90, 250, 700, 875, 1050);
	Property Red3 = new Property(24, 240, 0, 3, 0, 20, 40, 100, 300, 750, 925, 1100);
	Bus Bus3 = new Bus(25, 200, 0, 0);
	Property Yellow1 = new Property(26, 260, 0, 3, 0, 22, 44, 110, 330, 800, 975, 1150);
	Property Yellow2 = new Property(27, 260, 0, 3, 0, 22, 44, 110, 330, 800, 975, 1150);
	Utility Utility2 = new Utility(28, 150, 0, 0);
	Property Yellow3 = new Property(29, 280, 0, 3, 0, 24, 48, 120, 360, 850, 1025, 1200);
	Special GoToJail = new Special(30, 3);
	Property Green1 = new Property(31, 300, 0, 4, 0, 26, 52, 130, 390, 900, 1100, 1275);
	Special Chest3 = new Special(32, 4);
	Property Green2 = new Property(33, 300, 0, 4, 0, 26, 52, 130, 390, 900, 1100, 1275);
	Property Green3 = new Property(34, 320, 0, 4, 0, 28, 56, 150, 450, 1000, 1200, 1400);
	Bus Bus4 = new Bus(35, 200, 0, 0);
	Special Chance3 = new Special(36, 5);
	Property Blue1 = new Property(37, 350, 0, 4, 0, 35, 70, 175, 500, 1100, 1300, 1500);
	Special Tax2 = new Special(38, 7);
	Property Blue2 = new Property(39, 400, 0, 4, 0, 50, 100, 200, 600, 1400, 1700, 2000);

	PlayerIcon icon1, icon2, icon3, icon4;

	public Game() {
		player1 = new Player("player1");
		player2 = new Player("player2");
		player3 = new Player("player3");
		player4 = new Player("player4");
		background = QuickLoad("GameBackground");
		zoom = QuickLoad("TileHome");
		P1 = QuickLoad("Player1Box");
		P2 = QuickLoad("Player2Box");
		P3 = QuickLoad("Player3Box");
		P4 = QuickLoad("Player4Box");
		menu = QuickLoad("GameMenu");
		popupbox = QuickLoadPNG("PopUpBox");
		menuUI = new UI();
		jail = new UI();
		popup = new UI();
		roll = new UI();

		roll.addButton("Roll", "Roll", 448, 319);

		popup.addButton("Buy", "Buy", 544, 319);
		popup.addButton("Auction", "Auction", 352, 319);

		menuUI.addButton("Menu", "Menu", 16, 400);
		menuUI.addButton("Mortgage", "Mortgage", 100, 400);
		menuUI.addButton("Trade", "Trade", 182, 400);
		menuUI.addButton("Build", "Build", 16, 448);
		menuUI.addButton("Destroy", "Destroy", 100, 448);
		menuUI.addButton("EndTurn", "EndTurn", 182, 448);

		jail.addButton("JailPay", "JailPay", 544, 319);
		jail.addButton("JailTry", "JailTry", 352, 319);

		icon1 = new PlayerIcon(QuickLoadPNG("Icon1"), 694, 431, 16, 16, 8);
		icon2 = new PlayerIcon(QuickLoadPNG("Icon2"), 694, 449, 16, 16, 8);
		icon3 = new PlayerIcon(QuickLoadPNG("Icon3"), 694, 467, 16, 16, 8);
		icon4 = new PlayerIcon(QuickLoadPNG("Icon4"), 694, 485, 16, 16, 8);

		createOrder();
		orderPlayers();
	}

	private void Delay(int time) {
		try {
			Thread.sleep(time); // 1000 milliseconds is one second.
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	private void randomDice(boolean random, int dice) {
		if (random)
			dice = rollDice();

		switch (dice) {
		case 1:
			diceTexture = QuickLoad("dice1");
			break;
		case 2:
			diceTexture = QuickLoad("dice2");
			break;
		case 3:
			diceTexture = QuickLoad("dice3");
			break;
		case 4:
			diceTexture = QuickLoad("dice4");
			break;
		case 5:
			diceTexture = QuickLoad("dice5");
			break;
		case 6:
			diceTexture = QuickLoad("dice6");
			break;
		}
	}

	private void rollAnimation(int x, int y) {
		randomDice(true, 0);
		DrawQuadTex(diceTexture, x, y, 64, 64);
		Delay(20);

	}

	private static int rollDice() {
		Random rdm = new Random();
		int dice;
		dice = rdm.nextInt(6) + 1;
		return dice;
	}

	private void DoRoll() {
		if (rolltimer == 10) {
			rolltimer = 0;
			doroll = false;
		}
		rolltimer++;
		rollAnimation(416, 224);
		rollAnimation(544, 224);
		if (!doroll) {
			dice1 = rollDice();
			dice2 = rollDice();
			displayDice = true;
		}
	}

	private void randomizeArray(int array[], int n) {
		int i, aux, dice;
		Random rdm = new Random();
		for (i = 1; i < n; i++) {
			dice = rdm.nextInt(n);

			aux = array[i];
			array[i] = array[dice];
			array[dice] = aux;
		}
	}

	private void payProperty(Player player, Property property) {
		clicked2 = false;
		switch (property.getOwner()) {
		case 0:
			if (!auction) {
				Popup = true;
				DrawQuadTex(popupbox, 324, 128, 512, 256);
				DrawQuadTex(zoom, 480, 158, 64, 128);
				popup.draw();
				popUpButtons();
				if (clicked2 && player.getMoney() >= property.getPrice()) {
					player.setMoney(player.getMoney() - property.getPrice());
					property.setOwner(player.getOwner());
					Popup = false;
					paid = true;
				}
			} else {

			}
			break;
		case 1:
			if (player.getOwner() != 1) {
				player.setMoney(player.getMoney() - property.payment());
				player1.setMoney(player1.getMoney() + property.payment());
			}
			paid = true;
			break;
		case 2:
			if (player.getOwner() != 2) {
				player.setMoney(player.getMoney() - property.payment());
				player2.setMoney(player2.getMoney() + property.payment());
			}
			paid = true;
			break;
		case 3:
			if (player.getOwner() != 3) {
				player.setMoney(player.getMoney() - property.payment());
				player3.setMoney(player3.getMoney() + property.payment());
			}
			paid = true;
			break;
		case 4:
			if (player.getOwner() != 4) {
				player.setMoney(player.getMoney() - property.payment());
				player4.setMoney(player4.getMoney() + property.payment());
			}
			paid = true;
			break;
		}
	}

	private void payBus(Player player, Bus bus) {
		clicked2 = false;
		switch (bus.getOwner()) {
		case 0:
			if (!auction) {
				Popup = true;
				DrawQuadTex(popupbox, 324, 128, 512, 256);
				DrawQuadTex(zoom, 480, 158, 64, 128);
				popup.draw();
				popUpButtons();
				if (clicked2 && player.getMoney() >= 200) {
					player.setMoney(player.getMoney() - 200);
					bus.setOwner(player.getOwner());
					paid = true;
					Popup = false;
				}
			} else {

			}
			break;
		case 1:
			if (player.getOwner() != 1) {
				player.setMoney(player.getMoney() - bus.payment());
				player1.setMoney(player1.getMoney() + bus.payment());
			}
			paid = true;
			break;
		case 2:
			if (player.getOwner() != 2) {
				player.setMoney(player.getMoney() - bus.payment());
				player2.setMoney(player2.getMoney() + bus.payment());
			}
			paid = true;
			break;
		case 3:
			if (player.getOwner() != 3) {
				player.setMoney(player.getMoney() - bus.payment());
				player3.setMoney(player3.getMoney() + bus.payment());
			}
			paid = true;
			break;
		case 4:
			if (player.getOwner() != 4) {
				player.setMoney(player.getMoney() - bus.payment());
				player4.setMoney(player4.getMoney() + bus.payment());
			}
			paid = true;
			break;
		}
	}

	private void payUtility(Player player, Utility utility) {
		clicked2 = false;
		switch (utility.getOwner()) {
		case 0:
			if (!auction) {
				Popup = true;
				DrawQuadTex(popupbox, 324, 128, 512, 256);
				DrawQuadTex(zoom, 480, 158, 64, 128);
				popup.draw();
				popUpButtons();
				if (clicked2 && player.getMoney() >= 150) {
					player.setMoney(player.getMoney() - 150);
					utility.setOwner(player.getOwner());
					Popup = false;
					paid = true;
				}
			} else {

			}
			break;
		case 1:
			if (player.getOwner() != 1) {
				player.setMoney(player.getMoney() - utility.payment(dice1 + dice2));
				player1.setMoney(player1.getMoney() + utility.payment(dice1 + dice2));
			}
			paid = true;
			break;
		case 2:
			if (player.getOwner() != 2) {
				player.setMoney(player.getMoney() - utility.payment(dice1 + dice2));
				player2.setMoney(player2.getMoney() + utility.payment(dice1 + dice2));
			}
			paid = true;
			break;
		case 3:
			if (player.getOwner() != 3) {
				player.setMoney(player.getMoney() - utility.payment(dice1 + dice2));
				player3.setMoney(player3.getMoney() + utility.payment(dice1 + dice2));
			}
			paid = true;
			break;
		case 4:
			if (player.getOwner() != 4) {
				player.setMoney(player.getMoney() - utility.payment(dice1 + dice2));
				player4.setMoney(player4.getMoney() + utility.payment(dice1 + dice2));
			}
			paid = true;
			break;
		}
	}

	private void payTax(Player player, int tax){
		taxmoney += tax;
		player.setMoney(player.getMoney() - tax);
		paid = true;
	}
	
	private void doSpecial(Player player, Special special) {
		switch (special.getType()) {
		default:
			paid = true;
			break;
		}
	}

	private void posSearch(Player player) {
		switch (player.getPos()) {
		case 0:
			player.setMoney(player.getMoney() + 200);
			paid = true;
			break;
		case 1:
			zoom = QuickLoad("TileBrown1");
			payProperty(player, Brown1);
			break;
		case 2:
			doSpecial(player, Chest1);
			break;
		case 3:
			zoom = QuickLoad("TileBrown2");
			payProperty(player, Brown2);
			break;
		case 4:
			payTax(player, 200);
			break;
		case 5:
			zoom = QuickLoad("TileBus1");
			payBus(player, Bus1);
			break;
		case 6:
			zoom = QuickLoad("TileTeal1");
			payProperty(player, Teal1);
			break;
		case 7:
			doSpecial(player, Chance1);
			break;
		case 8:
			zoom = QuickLoad("TileTeal2");
			payProperty(player, Teal2);
			break;
		case 9:
			zoom = QuickLoad("TileTeal3");
			payProperty(player, Teal3);
			break;
		case 11:
			zoom = QuickLoad("TilePink1");
			payProperty(player, Pink1);
			break;
		case 12:
			zoom = QuickLoad("TileUtility1");
			payUtility(player, Utility1);
			break;
		case 13:
			zoom = QuickLoad("TilePink2");
			payProperty(player, Pink2);
			break;
		case 14:
			zoom = QuickLoad("TilePink3");
			payProperty(player, Pink3);
			break;
		case 15:
			zoom = QuickLoad("TileBus2");
			payBus(player, Bus2);
			break;
		case 16:
			zoom = QuickLoad("TileOrange1");
			payProperty(player, Orange1);
			break;
		case 17:
			doSpecial(player, Chest2);
			break;
		case 18:
			zoom = QuickLoad("TileOrange2");
			payProperty(player, Orange2);
			break;
		case 19:
			zoom = QuickLoad("TileOrange3");
			payProperty(player, Orange3);
			break;
		case 20:
			if (Rules.getInstance().isLuckyTile())
				player.setMoney(player.getMoney() + taxmoney);
			break;
		case 21:
			zoom = QuickLoad("TileRed1");
			payProperty(player, Red1);
			break;
		case 22:
			doSpecial(player, Chance2);
			break;
		case 23:
			zoom = QuickLoad("TileRed2");
			payProperty(player, Red2);
			break;
		case 24:
			zoom = QuickLoad("TileRed3");
			payProperty(player, Red3);
			break;
		case 25:
			zoom = QuickLoad("TileBus3");
			payBus(player, Bus3);
			break;
		case 26:
			zoom = QuickLoad("TileYellow1");
			payProperty(player, Yellow1);
			break;
		case 27:
			zoom = QuickLoad("TileYellow2");
			payProperty(player, Yellow2);
			break;
		case 28:
			zoom = QuickLoad("TileUtility2");
			payUtility(player, Utility2);
			break;
		case 29:
			zoom = QuickLoad("TileYellow3");
			payProperty(player, Yellow3);
			break;
		case 30:
			player.setJail(true);
			player.setPos(10);
			break;
		case 31:
			zoom = QuickLoad("TileGreen1");
			payProperty(player, Green1);
			break;
		case 32:
			zoom = QuickLoad("TileGreen2");
			payProperty(player, Green2);
			break;
		case 33:
			doSpecial(player, Chest3);
			break;
		case 34:
			zoom = QuickLoad("TileGreen3");
			payProperty(player, Green3);
			break;
		case 35:
			zoom = QuickLoad("TileBus4");
			payBus(player, Bus4);
			break;
		case 36:
			doSpecial(player, Chance3);
			break;
		case 37:
			zoom = QuickLoad("TileBlue1");
			payProperty(player, Blue1);
			break;
		case 38:
			payTax(player, 100);
			break;
		case 39:
			zoom = QuickLoad("TileBlue2");
			payProperty(player, Blue2);
			break;
		}
	}

	private void orderPlayers() {
		Player aux;
		if (Order[0] != 1)
			if (Order[0] == 2) {
				aux = player2;
				player2 = player1;
				player1 = aux;
				if (Order[1] == 3) {
					aux = player2;
					player2 = player3;
					player3 = aux;
					if (Order[2] == 4) {
						aux = player4;
						player4 = player3;
						player3 = aux;
					}
				} else if (Order[1] == 4) {
					aux = player2;
					player2 = player4;
					player4 = aux;
					if (Order[2] == 1) {
						aux = player4;
						player4 = player3;
						player3 = aux;
					}
				}
			} else if (Order[0] == 3) {
				aux = player3;
				player3 = player1;
				player1 = aux;
				if (Order[1] == 1) {
					aux = player2;
					player2 = player3;
					player3 = aux;
					if (Order[2] == 4) {
						aux = player3;
						player3 = player4;
						player4 = aux;
					}
				} else if (Order[1] == 4) {
					aux = player2;
					player2 = player4;
					player4 = aux;
					if (Order[2] == 2) {
						aux = player3;
						player3 = player4;
						player4 = aux;
					}
				}
			} else if (Order[0] == 4) {
				aux = player4;
				player4 = player1;
				player1 = aux;
				if (Order[1] == 3) {
					aux = player2;
					player2 = player3;
					player3 = aux;
					if (Order[2] == 1) {
						aux = player3;
						player3 = player4;
						player4 = aux;
					}
				} else if (Order[1] == 1) {
					aux = player2;
					player2 = player4;
					player4 = aux;
					if (Order[2] == 2) {
						aux = player3;
						player3 = player4;
						player4 = aux;
					}
				}
			} else if (Order[1] != 2) {
				if (Order[1] == 3) {
					aux = player2;
					player2 = player3;
					player3 = aux;
					if (Order[2] == 4) {
						aux = player3;
						player3 = player4;
						player4 = aux;
					}
				} else if (Order[1] == 4) {
					aux = player2;
					player2 = player4;
					player4 = aux;
					if (Order[2] == 2) {
						aux = player3;
						player3 = player4;
						player4 = aux;
					}
				}
			} else if (Order[2] != 3) {
				aux = player3;
				player3 = player4;
				player4 = aux;
			}
		player1.setOwner(1);
		player2.setOwner(2);
		player3.setOwner(3);
		player4.setOwner(4);
		switch (nrPlayers) {
		case 2:
			player4.setBankrupt(true);
			player3.setBankrupt(true);
			break;
		case 3:
			player4.setBankrupt(true);
			break;
		}
	}

	private boolean endGame() {
		if (nrPlayers != 1)
			return false;
		else
			return true;
	}

	public boolean isDouble(int dice1, int dice2) {
		if (dice1 == dice2)
			return true;
		else
			return false;
	}

	public void PlayerTurn(Player player) {
		clicked = false;

		if (!player.isBankrupt()) {
			if (!doroll)
				switch (phase) {
				case 1:
					if (player.isJail()) {
						Popup = true;
						jail.draw();
						jailButtons(player);
						if (clicked)
							phase = 2;
					} else if (player.getTries() == 0) {
						Popup = true;
						roll.draw();
						rollButton(player);
						if (clicked) {
							phase = 2;
						}
					} else if (player.getTries() > 0 && isdouble) {
						Popup = true;
						roll.draw();
						rollButton(player);
						if (clicked) {
							phase = 2;
						}
					}
					break;
				case 2:
					isdouble = isDouble(dice1, dice2);
					if (player.getTries() == 3 && isdouble) {
						player.setJail(true);
						Popup = true;
						phase = 4;
						player.setPos(10);
					} else if (player.getTries() != 0) {
						player.setPos(player.getPos() + dice1 + dice2);
						phase = 3;
					}
					break;
				case 3:
					if (player.getPos() >= 40) {
						player.setPos(player.getPos() - 40);
						player.setMoney(player.getMoney() + 200);
					}
					posSearch(player);
					if (paid)
						if (isdouble) {
							displayDice = false;
							phase = 1;
							paid = false;
						} else
							phase = 4;
					break;
				case 4:
					paid = false;
					player.setTries(0);
					break;
				}

		} else {
			Delay(200);
			paid = false;
			clicked2 = false;
			clicked = false;
			currentPlayer++;
			if (currentPlayer == 4)
				currentPlayer = 1;
		}
	}

	public void createOrder() {
		if (Rules.getInstance().getOrder() == 2) {
			Order[0] = 1;
			Order[1] = 2;
			Order[2] = 3;
			Order[3] = 4;
		} else
			randomizeArray(Order, 4);
	}

	public void getBackground() {
		DrawQuadTex(background, 0, 0, 1024, 512);
		DrawQuadTex(P1, 0, 0, 128, 256);
		DrawQuadTex(P2, 128, 0, 128, 256);
		DrawQuadTex(P3, 0, 192, 128, 256);
		DrawQuadTex(P4, 128, 192, 128, 256);
		DrawQuadTex(menu, 0, 384, 256, 128);
		if (doroll)
			DoRoll();
		else
			rolltimer = 0;
		if (displayDice) {
			randomDice(false, dice1);
			DrawQuadTex(diceTexture, 416, 224, 64, 64);
			randomDice(false, dice2);
			DrawQuadTex(diceTexture, 544, 224, 64, 64);
		}
		if (Popup) {
			DrawQuadTex(popupbox, 384, 128, 384, 256);
		}
		icon1.Draw();
		icon2.Draw();
		icon3.Draw();
		icon4.Draw();
		if (!Popup) {
			if (displayBigTile())
				DrawQuadTex(zoom, 384, 128, 256, 256);
			if (displaySmallTile())
				DrawQuadTex(zoom, 448, 128, 128, 256);
		}
	}

	private boolean displayBigTile() {
		float mouseY = HEIGHT - Mouse.getY() - 1;
		if (Mouse.getX() > 265 && Mouse.getX() < 341 && mouseY > 9 && mouseY < 85) {
			zoom = QuickLoad("TileParking");
			return true;
		}
		if (Mouse.getX() > 683 && Mouse.getX() < 759 && mouseY > 9 && mouseY < 85) {
			zoom = QuickLoad("TileGoToJail");
			return true;
		}
		if (Mouse.getX() > 265 && Mouse.getX() < 341 && mouseY > 427 && mouseY < 503) {
			zoom = QuickLoad("TileJail");
			return true;
		}
		if (Mouse.getX() > 683 && Mouse.getX() < 759 && mouseY > 427 && mouseY < 503) {
			zoom = QuickLoad("TileHome");
			return true;
		}
		return false;
	}

	private boolean displaySmallTile() {
		float mouseY = HEIGHT - Mouse.getY() - 1;
		// Tier 2
		if (Mouse.getX() > 265 && Mouse.getX() < 341 && mouseY > 85 && mouseY < 123) {
			zoom = QuickLoad("TileOrange3");
			return true;
		}
		if (Mouse.getX() > 265 && Mouse.getX() < 341 && mouseY > 123 && mouseY < 161) {
			zoom = QuickLoad("TileOrange2");
			return true;
		}
		if (Mouse.getX() > 265 && Mouse.getX() < 341 && mouseY > 161 && mouseY < 199) {
			zoom = QuickLoad("TileChest");
			return true;
		}
		if (Mouse.getX() > 265 && Mouse.getX() < 341 && mouseY > 199 && mouseY < 237) {
			zoom = QuickLoad("TileOrange1");
			return true;
		}
		if (Mouse.getX() > 265 && Mouse.getX() < 341 && mouseY > 237 && mouseY < 275) {
			zoom = QuickLoad("TileBus2");
			return true;
		}
		if (Mouse.getX() > 265 && Mouse.getX() < 341 && mouseY > 275 && mouseY < 313) {
			zoom = QuickLoad("TilePink3");
			return true;
		}
		if (Mouse.getX() > 265 && Mouse.getX() < 341 && mouseY > 313 && mouseY < 351) {
			zoom = QuickLoad("TilePink2");
			return true;
		}
		if (Mouse.getX() > 265 && Mouse.getX() < 341 && mouseY > 351 && mouseY < 389) {
			zoom = QuickLoad("TileUtility1");
			return true;
		}
		if (Mouse.getX() > 265 && Mouse.getX() < 341 && mouseY > 389 && mouseY < 427) {
			zoom = QuickLoad("TilePink1");
			return true;
		}
		// Tier 4
		if (Mouse.getX() > 683 && Mouse.getX() < 759 && mouseY > 85 && mouseY < 123) {
			zoom = QuickLoad("TileGreen1");
			return true;
		}
		if (Mouse.getX() > 683 && Mouse.getX() < 759 && mouseY > 123 && mouseY < 161) {
			zoom = QuickLoad("TileGreen2");
			return true;
		}
		if (Mouse.getX() > 683 && Mouse.getX() < 759 && mouseY > 161 && mouseY < 199) {
			zoom = QuickLoad("TileChest");
			return true;
		}
		if (Mouse.getX() > 683 && Mouse.getX() < 759 && mouseY > 199 && mouseY < 237) {
			zoom = QuickLoad("TileGreen3");
			return true;
		}
		if (Mouse.getX() > 683 && Mouse.getX() < 759 && mouseY > 237 && mouseY < 275) {
			zoom = QuickLoad("TileBus4");
			return true;
		}
		if (Mouse.getX() > 683 && Mouse.getX() < 759 && mouseY > 275 && mouseY < 313) {
			zoom = QuickLoad("TileChance");
			return true;
		}
		if (Mouse.getX() > 683 && Mouse.getX() < 759 && mouseY > 313 && mouseY < 351) {
			zoom = QuickLoad("TileBlue1");
			return true;
		}
		if (Mouse.getX() > 683 && Mouse.getX() < 759 && mouseY > 351 && mouseY < 389) {
			zoom = QuickLoad("TileTax2");
			return true;
		}
		if (Mouse.getX() > 683 && Mouse.getX() < 759 && mouseY > 389 && mouseY < 427) {
			zoom = QuickLoad("TileBlue2");
			return true;
		}
		// Tier 3
		if (Mouse.getX() > 341 && Mouse.getX() < 379 && mouseY > 9 && mouseY < 85) {
			zoom = QuickLoad("TileRed1");
			return true;
		}
		if (Mouse.getX() > 379 && Mouse.getX() < 417 && mouseY > 9 && mouseY < 85) {
			zoom = QuickLoad("TileChance");
			return true;
		}
		if (Mouse.getX() > 417 && Mouse.getX() < 455 && mouseY > 9 && mouseY < 85) {
			zoom = QuickLoad("TileRed2");
			return true;
		}
		if (Mouse.getX() > 455 && Mouse.getX() < 493 && mouseY > 9 && mouseY < 85) {
			zoom = QuickLoad("TileRed3");
			return true;
		}
		if (Mouse.getX() > 493 && Mouse.getX() < 531 && mouseY > 9 && mouseY < 85) {
			zoom = QuickLoad("TileBus3");
			return true;
		}
		if (Mouse.getX() > 531 && Mouse.getX() < 569 && mouseY > 9 && mouseY < 85) {
			zoom = QuickLoad("TileYellow1");
			return true;
		}
		if (Mouse.getX() > 569 && Mouse.getX() < 607 && mouseY > 9 && mouseY < 85) {
			zoom = QuickLoad("TileYellow2");
			return true;
		}
		if (Mouse.getX() > 607 && Mouse.getX() < 645 && mouseY > 9 && mouseY < 85) {
			zoom = QuickLoad("TileUtility2");
			return true;
		}
		if (Mouse.getX() > 645 && Mouse.getX() < 683 && mouseY > 9 && mouseY < 85) {
			zoom = QuickLoad("TileYellow3");
			return true;
		}
		// Tier 1
		if (Mouse.getX() > 341 && Mouse.getX() < 379 && mouseY > 427 && mouseY < 503) {
			zoom = QuickLoad("TileTeal3");
			return true;
		}
		if (Mouse.getX() > 379 && Mouse.getX() < 417 && mouseY > 427 && mouseY < 503) {
			zoom = QuickLoad("TileTeal2");
			return true;
		}
		if (Mouse.getX() > 417 && Mouse.getX() < 455 && mouseY > 427 && mouseY < 503) {
			zoom = QuickLoad("TileChance");
			return true;
		}
		if (Mouse.getX() > 455 && Mouse.getX() < 493 && mouseY > 427 && mouseY < 503) {
			zoom = QuickLoad("TileTeal1");
			return true;
		}
		if (Mouse.getX() > 493 && Mouse.getX() < 531 && mouseY > 427 && mouseY < 503) {
			zoom = QuickLoad("TileBus1");
			return true;
		}
		if (Mouse.getX() > 531 && Mouse.getX() < 569 && mouseY > 427 && mouseY < 503) {
			zoom = QuickLoad("TileTax1");
			return true;
		}
		if (Mouse.getX() > 569 && Mouse.getX() < 607 && mouseY > 427 && mouseY < 503) {
			zoom = QuickLoad("TileBrown2");
			return true;
		}
		if (Mouse.getX() > 607 && Mouse.getX() < 645 && mouseY > 427 && mouseY < 503) {
			zoom = QuickLoad("TileChest");
			return true;
		}
		if (Mouse.getX() > 645 && Mouse.getX() < 683 && mouseY > 427 && mouseY < 503) {
			zoom = QuickLoad("TileBrown1");
			return true;
		}
		return false;
	}

	private void updateButtons() {
		if (Mouse.isButtonDown(0)) {
			if (!Popup) {
				if (menuUI.isButtonClicked("Menu")) {
					Popup = true;
				}
				if (menuUI.isButtonClicked("Mortgage")) {
					Popup = true;
				}
				if (menuUI.isButtonClicked("Trade")) {
					Popup = true;
				}
				if (menuUI.isButtonClicked("Build")) {
					Popup = true;
				}
				if (menuUI.isButtonClicked("Destroy")) {
					Popup = true;
				}
				if (menuUI.isButtonClicked("EndTurn")) {
					Delay(200);
					System.out.println("player1 pos" + player1.getPos() + " money " + player1.getMoney() + " "
							+ player1.isBankrupt());
					System.out.println("player2 pos" + player2.getPos() + " money " + player2.getMoney() + " "
							+ player2.isBankrupt());
					System.out.println("player3 pos" + player3.getPos() + " money " + player3.getMoney() + " "
							+ player3.isBankrupt());
					System.out.println("player4 pos" + player4.getPos() + " money " + player4.getMoney() + " "
							+ player4.isBankrupt());

					currentPlayer++;
					if (currentPlayer == 5)
						currentPlayer = 1;
					phase = 1;
					displayDice = false;
				}

			}

		} else {

		}
	}

	private void rollButton(Player player) {
		if (Mouse.isButtonDown(0)) {
			if (roll.isButtonClicked("Roll")) {
				Delay(200);
				Popup = false;
				doroll = true;
				DoRoll();
				clicked = true;
				player.setTries(player.getTries() + 1);
			}
		}
	}

	private void jailButtons(Player player) {
		if (Mouse.isButtonDown(0)) {
			if (jail.isButtonClicked("JailTry")) {
				Delay(200);
				player.setTries(1);
				Popup = false;
				doroll = true;
				DoRoll();
				clicked = true;
				if (isDouble(dice1, dice2)) {
					player.setJail(false);
					isdouble = true;
				}

			}
			if (jail.isButtonClicked("JailPay")) {
				Delay(200);
				player.setMoney(player.getMoney() - 50);
				player.setTries(1);
				player.setJail(false);
				Popup = false;
				doroll = true;
				DoRoll();
				clicked = true;
				isdouble = isDouble(dice1, dice2);
			}
		}
	}

	private void popUpButtons() {
		if (Mouse.isButtonDown(0)) {
			if (popup.isButtonClicked("Buy")) {
				Delay(200);
				clicked2 = true;
			}
			if (popup.isButtonClicked("Auction")) {
				Delay(200);
				Popup = false;
				auction = true;
			}
		}
	}

	public void update() {
		getBackground();
		updateButtons();
		menuUI.draw();

		if (endGame()) {
			System.out.println("winner");
			StateManager.setState(GameState.ENDGAME);
		} else
			switch (currentPlayer) {
			case 1:
				PlayerTurn(player1);
				break;
			case 2:
				PlayerTurn(player2);
				break;
			case 3:
				PlayerTurn(player3);
				break;
			case 4:
				PlayerTurn(player4);
				break;
			}

	}

}
