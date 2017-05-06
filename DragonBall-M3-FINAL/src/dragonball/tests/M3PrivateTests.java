package dragonball.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import org.junit.Test;

import dragonball.model.attack.Attack;
import dragonball.model.attack.MaximumCharge;
import dragonball.model.attack.PhysicalAttack;
import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.battle.Battle;
import dragonball.model.battle.BattleEvent;
import dragonball.model.battle.BattleListener;
import dragonball.model.cell.Cell;
import dragonball.model.cell.Collectible;
import dragonball.model.cell.CollectibleCell;
import dragonball.model.cell.EmptyCell;
import dragonball.model.cell.FoeCell;
import dragonball.model.character.fighter.Earthling;
import dragonball.model.character.fighter.Frieza;
import dragonball.model.character.fighter.Namekian;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.dragon.Dragon;
import dragonball.model.exceptions.DragonBallException;
import dragonball.model.exceptions.DuplicateAttackException;
import dragonball.model.exceptions.InvalidAssignAttackException;
import dragonball.model.exceptions.InvalidFormatException;
import dragonball.model.exceptions.MapIndexOutOfBoundsException;
import dragonball.model.exceptions.MaximumAttacksLearnedException;
import dragonball.model.exceptions.MissingFieldException;
import dragonball.model.exceptions.NotEnoughAbilityPointsException;
import dragonball.model.exceptions.NotEnoughResourcesException;
import dragonball.model.exceptions.NotEnoughSenzuBeansException;
import dragonball.model.exceptions.UnknownAttackTypeException;
import dragonball.model.game.Game;
import dragonball.model.game.GameListener;
import dragonball.model.game.GameState;
import dragonball.model.player.Player;
import dragonball.model.player.PlayerListener;
import dragonball.model.world.World;
import dragonball.model.world.WorldListener;

public class M3PrivateTests {

	private int thrown = 0;

	@Test(timeout = 1000)
	public void testSaveMethodStructure() throws Exception {
		Method[] methods = Game.class.getDeclaredMethods();
		assertTrue("Game Class should have \"save\" method",
				containsMethodName(methods, "save"));
		Method m;
		try {
			m = Game.class.getDeclaredMethod("save", String.class);
			assertTrue(
					"incorrect return type for \"save\" method in Game Class.",
					m.getReturnType().equals(Void.TYPE));

		} catch (Exception e) {
			fail("Missing \"save\" method in Game Class which takes a String input as a parameter");
		}
	}

	@Test(timeout = 1000)
	public void testUnknownAttackTypeExceptionExtendsInvalidFormatException()
			throws Exception {
		assertEquals(
				"UnknownAttackTypeException class should extend InvalidFormatException",
				InvalidFormatException.class,
				UnknownAttackTypeException.class.getSuperclass());
	}

	@Test(timeout = 1000)
	public void testUnknownAttackTypeExceptionClassVariables() throws Exception {
		Field f = null;
		boolean thrown = false;
		try {
			f = UnknownAttackTypeException.class
					.getDeclaredField("unknownType");
		} catch (NoSuchFieldException e) {
			thrown = true;
		}
		assertFalse(
				"there should be \"unknownType\" instance variable in class UnknownAttackTypeException",
				thrown);
		assertEquals(
				"\"unknownType\" instance variable in class UnknownAttackTypeException should be of type String",
				f.getType(), String.class);
	}

	@Test(timeout = 1000)
	public void testUnknownAttackTypeExceptionClassVariablesAccessibility()
			throws Exception {

		Field f = UnknownAttackTypeException.class
				.getDeclaredField("unknownType");
		assertEquals(
				"\"unknownType\" instance variable in class UnknownAttackTypeException should not be accessed outside that class",
				2, f.getModifiers());
	}

	@Test(timeout = 1000)
	public void testUnknownAttackTypeExceptionClassREADVariables()
			throws Exception {
		Method[] methods = UnknownAttackTypeException.class
				.getDeclaredMethods();

		assertTrue(
				"The \"unknownType\" instance variable in class UnknownAttackTypeException is a READ variable.",
				containsMethodName(methods, "getUnknownType"));

		try {
			Method m = UnknownAttackTypeException.class
					.getDeclaredMethod("getUnknownType");
			assertTrue(
					"incorrect return type for \"getUnknownType\" method in UnknownAttackTypeException class.",
					m.getReturnType().equals(String.class));
		} catch (Exception e) {
			fail("Missing \"getUnknownType\" method in UnknownAttackTypeException class which takes no input parameters.");
		}

		assertFalse(
				"The \"unknownType\" instance variable in class UnknownAttackTypeException is a READ ONLY variable.",
				containsMethodName(methods, "setUnknownType"));
	}

	@Test(timeout = 1000)
	public void testUnknownAttackTypeExceptionFirstConstructor()
			throws Exception {
		Class aClass = UnknownAttackTypeException.class;
		boolean thrown = false;
		try {
			Constructor constructor = aClass.getConstructor(new Class[] {
					String.class, int.class, String.class });
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with 3 parameters in UnknownAttackTypeException class.",
				thrown);
	}

	@Test(timeout = 1000)
	public void testUnknownAttackTypeExceptionSecondConstructor()
			throws Exception {
		Class aClass = UnknownAttackTypeException.class;
		boolean thrown = false;
		try {
			Constructor constructor = aClass.getConstructor(new Class[] {
					String.class, String.class, int.class, String.class });
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with 4 parameters in UnknownAttackTypeException class.",
				thrown);
	}

	@Test(timeout = 1000)
	public void testUnknownAttackTypeFirstConstructorInitialization()
			throws Exception {

		UnknownAttackTypeException e = new UnknownAttackTypeException(
				"sourceFile", 1, "unknownType");
		assertEquals(
				"The constructor of UnknownAttackTypeException class should initialize the sourceFile instance variable correctly by calling super.",
				"sourceFile", e.getSourceFile());

		assertEquals(
				"The constructor of UnknownAttackTypeException class should initialize the sourceLine instance variable correctly by calling super.",
				1, e.getSourceLine());

		assertEquals(
				"The constructor of UnknownAttackTypeException class should initialize the unknownType instance variable correctly.",
				"unknownType", e.getUnknownType());
	}

	@Test(timeout = 1000)
	public void testUnknownAttackTypeSecondConstructorInitialization()
			throws Exception {

		UnknownAttackTypeException e = new UnknownAttackTypeException(
				"Message", "sourceFile", 1, "unknownType");

		assertTrue(
				"The constructor of UnknownAttackTypeException class should initialize the message instance variable correctly by calling super.",
				e.getMessage().contains("Message"));

		assertEquals(
				"The constructor of UnknownAttackTypeException class should initialize the sourceFile instance variable correctly by calling super.",
				"sourceFile", e.getSourceFile());

		assertEquals(
				"The constructor of UnknownAttackTypeException class should initialize the sourceLine instance variable correctly by calling super.",
				1, e.getSourceLine());

		assertEquals(
				"The constructor of UnknownAttackTypeException class should initialize the unknownType instance variable correctly.",
				"unknownType", e.getUnknownType());
	}

	@Test(timeout = 1000)
	public void testNotEnoughResourcesExceptionExtendsDragonBallException()
			throws Exception {
		assertEquals(
				"NotEnoughResourcesException class should extend Exception",
				DragonBallException.class,
				NotEnoughResourcesException.class.getSuperclass());
	}

	@Test(timeout = 1000)
	public void testNotEnoughResourcesExceptionIsAbstract() throws Exception {
		assertTrue(
				"NotEnoughResourcesException class should be an abstract class",
				Modifier.isAbstract(NotEnoughResourcesException.class
						.getModifiers()));
	}

	@Test(timeout = 1000)
	public void testNotEnoughResourcesExceptionFirstConstructor()
			throws Exception {
		Class aClass = NotEnoughResourcesException.class;
		boolean thrown = false;
		try {
			Constructor constructor = aClass.getConstructor(new Class[] {});
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with no parameters in NotEnoughResourcesException class.",
				thrown);
	}

	@Test(timeout = 1000)
	public void testNotEnoughResourcesExceptionSecondConstructor()
			throws Exception {
		Class aClass = NotEnoughResourcesException.class;
		boolean thrown = false;
		try {
			Constructor constructor = aClass
					.getConstructor(new Class[] { String.class });
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with 1 parameter in NotEnoughResourcesException class.",
				thrown);
	}

	@Test(timeout = 1000)
	public void testNotEnoughAbilityPointsExtendsNotEnoughResources()
			throws Exception {
		assertEquals(
				"NotEnoughAbilityPointsException class should extend NotEnoughResourcesException",
				NotEnoughResourcesException.class,
				NotEnoughAbilityPointsException.class.getSuperclass());
	}

	@Test(timeout = 1000)
	public void testNotEnoughAbilityPointsExceptionConstructor()
			throws Exception {
		Class aClass = NotEnoughAbilityPointsException.class;
		boolean thrown = false;
		try {
			Constructor constructor = aClass.getConstructor(new Class[] {});
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with no parameters in NotEnoughAbilityPointsException class.",
				thrown);
	}

	@Test(timeout = 1000)
	public void testInvalidAssignAttackExceptionExtendsDragonBallException()
			throws Exception {
		assertEquals(
				"InvalidAssignAttackException class should extend DragonBallException",
				DragonBallException.class,
				InvalidAssignAttackException.class.getSuperclass());
	}

	@Test(timeout = 1000)
	public void testInvalidAssignAttackExceptionIsAbstract() throws Exception {
		assertTrue(
				"InvalidAssignAttackException class should be an abstract class",
				Modifier.isAbstract(InvalidAssignAttackException.class
						.getModifiers()));
	}

	@Test(timeout = 1000)
	public void testInvalidAssignAttackExceptionFirstConstructor()
			throws Exception {
		Class aClass = InvalidAssignAttackException.class;
		boolean thrown = false;
		try {
			Constructor constructor = aClass.getConstructor(new Class[] {});
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with no parameters in InvalidAssignAttackException class.",
				thrown);
	}

	@Test(timeout = 1000)
	public void testInvalidAssignAttackExceptionSecondConstructor()
			throws Exception {
		Class aClass = InvalidAssignAttackException.class;
		boolean thrown = false;
		try {
			Constructor constructor = aClass
					.getConstructor(new Class[] { String.class });
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with a String parameter in InvalidAssignAttackException class.",
				thrown);
	}

	@Test(timeout = 1000)
	public void testDuplicateAttackExceptionExtendsInvalidAssignAttack()
			throws Exception {
		assertEquals(
				"DuplicateAttackException class should extend predefined InvalidAssignAttackException",
				InvalidAssignAttackException.class,
				DuplicateAttackException.class.getSuperclass());
	}

	@Test(timeout = 1000)
	public void testDuplicateAttackExceptionClassVariables() throws Exception {
		Field f = null;
		boolean thrown = false;
		try {
			f = DuplicateAttackException.class.getDeclaredField("attack");
		} catch (NoSuchFieldException e) {
			thrown = true;
		}
		assertFalse(
				"there should be \"attack\" instance variable in class DuplicateAttackException",
				thrown);
		assertEquals(
				"\"attack\" instance variable in class DuplicateAttackException should be of type Attack",
				f.getType(), Attack.class);
	}

	@Test(timeout = 1000)
	public void testDuplicateAttackExceptionClassVariablesAccessibility()
			throws Exception {
		Field f = DuplicateAttackException.class.getDeclaredField("attack");
		assertEquals(
				"\"attack\" instance variable in class DuplicateAttackException should not be accessed outside that class",
				2, f.getModifiers());
	}

	@Test(timeout = 1000)
	public void testDuplicateAttackExceptionClassREADVariables()
			throws Exception {
		Method[] methods = DuplicateAttackException.class.getDeclaredMethods();

		assertTrue(
				"The \"attack\" instance variable in class DuplicateAttackException is a READ variable.",
				containsMethodName(methods, "getAttack"));
		try {
			Method m = DuplicateAttackException.class
					.getDeclaredMethod("getAttack");
			assertTrue(
					"incorrect return type for \"getAttack\" method in DuplicateAttackException class.",
					m.getReturnType().equals(Attack.class));
		} catch (Exception e) {
			fail("Missing \"getAttack\" method in DuplicateAttackException class which takes no input parameters.");
		}

		assertFalse(
				"The \"attack\" instance variable in class DuplicateAttackException is a READ ONLY variable.",
				containsMethodName(methods, "setAttack"));
	}

	@Test(timeout = 1000)
	public void testDuplicateAttackExceptionConstructor() throws Exception {
		Class aClass = DuplicateAttackException.class;
		boolean thrown = false;
		try {
			Constructor constructor = aClass
					.getConstructor(new Class[] { Attack.class });
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with 1 parameter in DuplicateAttackException class.",
				thrown);
	}

	@Test(timeout = 1000)
	public void testDuplicateAttackConstructorInitialization() throws Exception {
		Attack a = new PhysicalAttack();
		DuplicateAttackException e = new DuplicateAttackException(a);
		assertEquals(
				"The constructor of DuplicateAttackException class should initialize the attack instance variable correctly.",
				a, e.getAttack());
	}

	@Test(timeout = 1000)
	public void testMapIndexOutOfBoundsExceptionExtendsIndexOutOfBoundsException()
			throws Exception {
		assertEquals(
				"MapIndexOutOfBoundsException class should extend predefined IndexOutOfBoundsException",
				IndexOutOfBoundsException.class,
				MapIndexOutOfBoundsException.class.getSuperclass());
	}

	@Test(timeout = 1000)
	public void testMapIndexOutOfBoundsExceptionClassVariables()
			throws Exception {
		Field f = null;
		boolean thrown = false;
		try {
			f = MapIndexOutOfBoundsException.class.getDeclaredField("row");
		} catch (NoSuchFieldException e) {
			thrown = true;
		}
		assertFalse(
				"there should be \"row\" instance variable in class MapIndexOutOfBoundsException",
				thrown);
		assertEquals(
				"\"row\" instance variable in class MapIndexOutOfBoundsException should be of type int",
				f.getType(), int.class);

		thrown = false;
		try {
			f = MapIndexOutOfBoundsException.class.getDeclaredField("column");
		} catch (NoSuchFieldException e) {
			thrown = true;
		}
		assertFalse(
				"there should be \"column\" instance variable in class MapIndexOutOfBoundsException",
				thrown);
		assertEquals(
				"\"column\" instance variable in class MapIndexOutOfBoundsException should be of type int",
				f.getType(), int.class);

	}

	@Test(timeout = 1000)
	public void testMapIndexOutOfBoundsExceptionClassVariablesAccessibility()
			throws Exception {
		Field f = MapIndexOutOfBoundsException.class.getDeclaredField("row");
		assertEquals(
				"\"row\" instance variable in class MapIndexOutOfBoundsException should not be accessed outside that class",
				2, f.getModifiers());
		f = MapIndexOutOfBoundsException.class.getDeclaredField("column");
		assertEquals(
				"\"column\" instance variable in class MapIndexOutOfBoundsException should not be accessed outside that class",
				2, f.getModifiers());
	}

	@Test(timeout = 1000)
	public void testMapIndexOutOfBoundsExceptionClassREADVariables()
			throws Exception {
		Method[] methods = MapIndexOutOfBoundsException.class
				.getDeclaredMethods();

		assertTrue(
				"The \"row\" instance variable in class MapIndexOutOfBoundsException is a READ variable.",
				containsMethodName(methods, "getRow"));
		assertTrue(
				"The \"column\" instance variable in class MapIndexOutOfBoundsException is a READ variable.",
				containsMethodName(methods, "getColumn"));

		try {
			Method m = MapIndexOutOfBoundsException.class
					.getDeclaredMethod("getRow");
			assertTrue(
					"incorrect return type for \"getRow\" method in MapIndexOutOfBoundsException class.",
					m.getReturnType().equals(int.class));
		} catch (Exception e) {
			fail("Missing \"getRow\" method in MapIndexOutOfBoundsException class which takes no input parameters.");
		}

		try {
			Method m = MapIndexOutOfBoundsException.class
					.getDeclaredMethod("getColumn");
			assertTrue(
					"incorrect return type for \"getColumn\" method in MapIndexOutOfBoundsException class.",
					m.getReturnType().equals(int.class));
		} catch (Exception e) {
			fail("Missing \"getColumn\" method in MapIndexOutOfBoundsException class which takes no input parameters.");
		}

		assertFalse(
				"The \"row\" instance variable in class MapIndexOutOfBoundsException is a READ ONLY variable.",
				containsMethodName(methods, "setRow"));
		assertFalse(
				"The \"column\" instance variable in class MapIndexOutOfBoundsException is a READ ONLY variable.",
				containsMethodName(methods, "setColumn"));
	}

	@Test(timeout = 1000)
	public void testMapIndexOutOfBoundsExceptionConstructor() throws Exception {
		Class aClass = MapIndexOutOfBoundsException.class;
		boolean thrown = false;
		try {
			Constructor constructor = aClass.getConstructor(new Class[] {
					int.class, int.class });
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with 2 parameter in MapIndexOutOfBoundsException class.",
				thrown);
	}

	@Test(timeout = 1000)
	public void testMapIndexOutOfBoundsConstructorInitialization()
			throws Exception {
		MapIndexOutOfBoundsException e = new MapIndexOutOfBoundsException(1, 2);
		assertEquals(
				"The constructor of MapIndexOutOfBoundsException class should initialize the row instance variable correctly.",
				1, e.getRow());
		assertEquals(
				"The constructor of MapIndexOutOfBoundsException class should initialize the column instance variable correctly.",
				2, e.getColumn());
	}

	@Test(timeout = 1000)
	public void testMoveLeftOutOfBoundaries() throws Exception {
		Game g = new Game();
		World w = g.getWorld();
		clearWorld(w.getMap());

		Field f;

		f = w.getClass().getDeclaredField("playerRow");
		f.setAccessible(true);
		f.set(w, 9);

		f = w.getClass().getDeclaredField("playerColumn");
		f.setAccessible(true);
		f.set(w, 1);
		try {
			g.getWorld().moveLeft();
		} catch (MapIndexOutOfBoundsException e) {
			fail("No exception should be thrown when trying to move the player left in the map boundaries.");
		}

		try {
			g.getWorld().moveLeft();
		} catch (MapIndexOutOfBoundsException e) {
			assertTrue(
					"MoveLeft Method in the World class should include the row and column numbers of the fighter when attempting an invalid move in the message of the Exception thrown ",
					e.getMessage().contains("0")
							&& e.getMessage().contains("9"));
			return;
		}
		fail("moveLeft method in class World should throw the appropriate exception when moving out of boundaries");

	}

	@Test(timeout = 1000)
	public void testMoveDownOutOfBoundaries() throws Exception {
		Game g = new Game();
		World w = g.getWorld();
		clearWorld(w.getMap());

		Field f;

		f = w.getClass().getDeclaredField("playerRow");
		f.setAccessible(true);
		f.set(w, 8);

		f = w.getClass().getDeclaredField("playerColumn");
		f.setAccessible(true);
		f.set(w, 8);

		try {
			g.getWorld().moveDown();
		} catch (MapIndexOutOfBoundsException e) {
			fail("No exception should be thrown when trying to move the player down in the map boundaries.");
		}
		try {
			g.getWorld().moveDown();
		} catch (MapIndexOutOfBoundsException e) {
			assertTrue(
					"MoveDown Method in the World class should include the row and column numbers of the fighter when attempting an invalid move in the message of the Exception thrown ",
					e.getMessage().contains("9")
							&& e.getMessage().contains("8"));
			return;
		}
		fail("moveDown method in class World should throw the appropriate exception when moving out of boundaries");

	}

	@Test(timeout = 1000)
	public void testUltimateAssignAttackMethod_Throws_DuplicateAttackException()
			throws Exception {
		Earthling fighter1 = new Earthling("fighter1");
		ArrayList<UltimateAttack> ultimateAttacks1 = new ArrayList<UltimateAttack>();
		UltimateAttack u1 = new UltimateAttack("Ultimate1", 50);
		UltimateAttack u2 = new UltimateAttack("Ultimate2", 100);
		ultimateAttacks1.add(u1);
		ultimateAttacks1.add(u2);
		fighter1.setUltimateAttacks(ultimateAttacks1);

		ArrayList<PlayableFighter> fighters = new ArrayList<PlayableFighter>();
		fighters.add(fighter1);

		Player player = new Player("player");
		player.setFighters(fighters);

		try {
			player.assignAttack(fighter1, u1, u2);
		} catch (DuplicateAttackException e) {
			Attack learned = e.getAttack();
			assertEquals(
					"DuplicateAttackException constructor should intilaize the attack variable",
					u1, learned);
			return;
		}

		fail("assignAttack method in class Player should throw the appropriate Exceptions when trying to assign an attack to a fighter, while it already exists in his/her list of assigned attacks");
	}

	@Test(timeout = 1000)
	public void testSuperAssignAttackMethod_Throws_MaximumAttacksLearnedException()
			throws Exception {
		ArrayList<SuperAttack> superAttacks1 = new ArrayList<SuperAttack>();
		SuperAttack s1 = new SuperAttack("super1", 50);
		SuperAttack s2 = new SuperAttack("super2", 50);
		SuperAttack s3 = new SuperAttack("super2", 50);

		superAttacks1.add(s1);
		superAttacks1.add(s2);
		superAttacks1.add(s3);
		Earthling fighter1 = new Earthling("fighter1");
		fighter1.setSuperAttacks(superAttacks1);

		ArrayList<SuperAttack> superAttacks2 = new ArrayList<SuperAttack>();
		SuperAttack s4 = new SuperAttack("super4", 50);
		SuperAttack s5 = new SuperAttack("super5", 50);
		SuperAttack s6 = new SuperAttack("super6", 50);
		SuperAttack s7 = new SuperAttack("super7", 50);

		superAttacks2.add(s4);
		superAttacks2.add(s5);
		superAttacks2.add(s6);
		superAttacks2.add(s7);
		Earthling fighter2 = new Earthling("fighter2");
		fighter2.setSuperAttacks(superAttacks2);

		ArrayList<PlayableFighter> fighters = new ArrayList<PlayableFighter>();
		fighters.add(fighter1);
		fighters.add(fighter2);

		Player player = new Player("player");
		player.setFighters(fighters);

		SuperAttack superAttack = new SuperAttack("super", 50);

		try {
			player.assignAttack(fighter1, superAttack, s1);
		} catch (MaximumAttacksLearnedException e) {
			fail("assignAttack method in class Player should add the new Super Attack to the list of superAttacks of the input fighter; No Exception should be thrown");
		}

		SuperAttack superAttack1 = new SuperAttack("super1", 50);
		try {
			player.assignAttack(fighter1, superAttack1, null);
		} catch (MaximumAttacksLearnedException e) {
			fail("assignAttack method in class Player should add the new Super Attack to the list of superAttacks of the input fighter; No Exception should be thrown");
		}

		try {
			player.assignAttack(fighter2, superAttack, null);
		} catch (MaximumAttacksLearnedException e) {
			return;
		}
		fail("assignAttack method in class Player should should throw the appropriate Exceptions when trying to assign a super attack to the fighter while he/she already has the maximum possible number of attacks assigned");

		try {
			player.assignAttack(fighter2, superAttack, s5);
		} catch (MaximumAttacksLearnedException e) {
			return;
		}
		fail("assignAttack method in class Player should should throw the appropriate Exceptions when trying to assign a super attack to the fighter while he/she already has the maximum possible number of attacks assigned");

	}

	@Test(timeout = 1000)
	public void testLoadDragonsMethod_Throws_MissingFieldException()
			throws Exception {
		try {
			Game g = new Game();
			g.getDragons().clear();
			executeLoadDragons_InCorrectFile_MissingField(g, Game.class);
		} catch (Exception e) {
			assertTrue(
					"Load Dragons Method in the Game class should throw the appropriate Exception when a field is missing in the file",
					e.getCause() instanceof MissingFieldException);
			assertTrue(
					"Load Foes Method in the Game class should include the File name in the message of the Exception thrown when a field is missing in the file ",
					((MissingFieldException) e.getCause()).getMessage()
							.contains("DragonsTest.csv"));
			assertTrue(
					"Load Foes Method in the Game class should include the File line that has the error in the message of the Exception thrown, when a field is missing in the file ",
					((MissingFieldException) e.getCause()).getMessage()
							.contains("4"));

			assertTrue(
					"Load Foes Method in the Game class should include the number of the missing fields in the message of the Exception thrown, when a field(s) is/are missing in the file ",
					((MissingFieldException) e.getCause()).getMessage()
							.contains("1"));
		}

		try {
			Game g = new Game();
			g.getAttacks().clear();
			executeLoadDragons_CorrectFile_MissingField(g, Game.class);
		} catch (InvocationTargetException e) {
			fail("Load Dragons Method should work correctly if the file is correct; No Exception should be thrown");
		}

	}

	@Test(timeout = 1000)
	public void testGameConstructor_Handles_MissingFieldException_OnLoadFoes()
			throws Exception {
		thrown = 0;
		Game g = null;
		try {
			g = new Game() {

				public void loadFoes(String f) throws MissingFieldException {
					thrown++;
					if (thrown++ == 1) {
						throw new MissingFieldException(f, 0, 2);
					}
					if (thrown == 4) {
						try {
							super.loadFoes(f);
						} catch (IOException e) {

						}
					}
				}

			};

		} catch (Exception e) {
			fail("Game constructor should handle Exceptions thrown on Loading Foes");
		}

		ArrayList<NonPlayableFighter> testStrongFoes = g.getStrongFoes();

		ArrayList<NonPlayableFighter> testWeakFoes = g.getWeakFoes();

		assertEquals(
				"Load Foes should load from the auxiliary csv file when an exception is thrown",
				1, testStrongFoes.size());

		assertEquals(
				"Load Foes should load from the auxiliary csv file when an exception is thrown",
				1, testWeakFoes.size());

		assertEquals(
				"Strong foe name is not loaded correctly from the auxiliary CSV file of Foes",
				"Goku", testStrongFoes.get(0).getName());
		assertEquals(
				"Strong foe level is not loaded correctly from the auxiliary  CSV file of Foes",
				10, testStrongFoes.get(0).getLevel());
		assertEquals(
				"Strong foe maxHealthPoints is not loaded correctly from the auxiliary CSV file of Foes",
				3000, testStrongFoes.get(0).getMaxHealthPoints());
		assertEquals(
				"Strong foe blast damage is not loaded correctly from the auxiliary CSV file of Foes",
				350, testStrongFoes.get(0).getBlastDamage());
		assertEquals(
				"Strong foe physical damage is not loaded correctly from the auxiliary CSV file of Foes",
				400, testStrongFoes.get(0).getPhysicalDamage());
		assertEquals(
				"Strong foe maxKi is not loaded correctly from the CSV file of Foes",
				5, testStrongFoes.get(0).getMaxKi());
		assertEquals(
				"Strong foe maxStamina is not loaded correctly from the auxiliary CSV file of Foes",
				6, testStrongFoes.get(0).getMaxStamina());
		assertTrue(
				"\"Strong\" boolean for a Strong foe should be true while loading from the auxiliary CSV file of Foes",
				testStrongFoes.get(0).isStrong());

		assertEquals(
				"Super Attacks of Strong foe doesn't contain the same number of super attacks given in the auxiliary CSV file",
				1, testStrongFoes.get(0).getSuperAttacks().size());
		assertEquals(
				"Super Attack of Strong foe is not loaded correctly from the auxiliary CSV file of Foes",
				"Kamehameha", testStrongFoes.get(0).getSuperAttacks().get(0)
						.getName());

		assertEquals(
				"Ultimate Attacks of Strong foe doesn't contain the same number of ultimate attacks given in the auxiliary CSV file",
				2, testStrongFoes.get(0).getUltimateAttacks().size());
		assertEquals(
				"Ultimate Attack of Strong foe is not loaded correctly from the auxiliary CSV file of Foes",
				"Super Kamehameha", testStrongFoes.get(0).getUltimateAttacks()
						.get(0).getName());

		assertEquals(
				"Ultimate Attack of Strong foe is not loaded correctly from the auxiliary CSV file of Foes",
				"Spirit Bomb", testStrongFoes.get(0).getUltimateAttacks()
						.get(1).getName());

		assertEquals(
				"Weak foe name is not loaded correctly from the auxiliary CSV file of Foes",
				"Krillin", testWeakFoes.get(0).getName());
		assertEquals(
				"Weak foe level is not loaded correctly from the auxiliary CSV file of Foes",
				5, testWeakFoes.get(0).getLevel());
		assertEquals(
				"Weak foe maxHealthPoints is not loaded correctly from the auxiliary CSV file of Foes",
				1400, testWeakFoes.get(0).getMaxHealthPoints());
		assertEquals(
				"Weak foe blast damage is not loaded correctly from the auxiliary CSV file of Foes",
				150, testWeakFoes.get(0).getBlastDamage());
		assertEquals(
				"Weak foe physical damage is not loaded correctly from the auxiliary CSV file of Foes",
				200, testWeakFoes.get(0).getPhysicalDamage());
		assertEquals(
				"Weak foe maxKi is not loaded correctly from the auxiliary CSV file of Foes",
				4, testWeakFoes.get(0).getMaxKi());
		assertEquals(
				"Weak foe maxStamina is not loaded correctly from the auxiliary CSV file of Foes",
				5, testWeakFoes.get(0).getMaxStamina());
		assertFalse(
				"\"Strong\" boolean for a weak foe should be false while loading from the auxiliary CSV file of Foes",
				testWeakFoes.get(0).isStrong());

		assertEquals(
				"Super Attacks of Weak foe doesn't contain the same number of super attacks given in the auxiliary CSV file",
				1, testWeakFoes.get(0).getSuperAttacks().size());
		assertEquals(
				"Super Attack of Weak foe is not loaded correctly from the auxiliary CSV file of Foes",
				"Ki Blast Thrust", testWeakFoes.get(0).getSuperAttacks().get(0)
						.getName());

		assertEquals(
				"Ultimate Attacks of Weak foe doesn't contain the same number of ultimate attacks given in the auxiliary CSV file",
				1, testWeakFoes.get(0).getUltimateAttacks().size());
		assertEquals(
				"Ultimate Attack of Weak foe is not loaded correctly from the auxiliary CSV file of Foes",
				"Explosive Assault", testWeakFoes.get(0).getUltimateAttacks()
						.get(0).getName());

	}

	@Test(timeout = 1000)
	public void testGameConstructor_Handles_UnknownAttackTypeException()
			throws Exception {
		thrown = 0;
		Game g = null;
		try {
			g = new Game() {
				public void loadAttacks(String f) throws MissingFieldException,
						UnknownAttackTypeException {
					thrown++;
					if (thrown++ == 1) {
						throw new UnknownAttackTypeException(f, 0, "");
					}
					if (thrown == 4) {
						try {
							super.loadAttacks(f);
						} catch (IOException e) {

						}
					}

				}

			};
		} catch (Exception e) {
			fail("Game constructor should handle Exceptions thrown on Loading Attacks");
		}

		ArrayList<Attack> auxAttacks = g.getAttacks();

		assertEquals(
				"Load Attacks should load from the auxiliary csv file when an exception is thrown",
				1, auxAttacks.size());

		assertTrue(
				"Attack type is not loaded correctly from the auxiliary CSV file of Attacks",
				auxAttacks.get(0) instanceof MaximumCharge);
		assertEquals(
				"Attack name is not loaded correctly from the auxiliary CSV file of Attacks",
				"Maximum Charge", auxAttacks.get(0).getName());
		assertEquals(
				"Attack damage is not loaded correctly from the auxiliary CSV file of Attacks",
				0, auxAttacks.get(0).getDamage());

	}

	@Test(timeout = 1000)
	public void testThrowingNotEnoughSenzuBeanException() throws Exception {

		Game g = new Game();
		Battle b = new Battle(new Saiyan("saiyan"), new NonPlayableFighter(
				"foe", 1, 1, 1, 1, 1, 1, true, null, null));
		invokeSetter(b, BattleListener.class, g);
		Player p = new Player("");
		p.setSenzuBeans(0);
		Namekian n = new Namekian("namekian", 2, 100, 200, 300, 400, 500, 5, 6,
				600, null, null);

		ArrayList<PlayableFighter> fighters = new ArrayList<PlayableFighter>();
		fighters.add(n);
		p.setFighters(fighters);
		p.setActiveFighter(n);
		boolean thrown = false;
		try {
			b.use(p, Collectible.SENZU_BEAN);
		} catch (NotEnoughSenzuBeansException e) {
			thrown = true;
			if (e.getMessage() == null)
				fail("NotEnoughSenzuBeansException should be initialized with a descriptive customized message");

		}
		assertTrue(
				"NotEnoughSenzuBeansException should be thrown if the user tries to use a senzubean when there are zero senzubeans",
				thrown);

		thrown = false;
		try {
			p.setSenzuBeans(2);
			b.use(p, Collectible.SENZU_BEAN);
		} catch (NotEnoughSenzuBeansException e) {
			thrown = true;
		}
		assertFalse(
				"NotEnoughSenzuBeansException should not be thrown if the user tries to use a senzubean when there are enough senzubeans",
				thrown);

	}

	@Test(timeout = 1000)
	public void testThrowingNotEnoughAbilityPointsException() throws Exception {

		Earthling fighter1 = new Earthling("fighter1", 1, 1, 10, 1250, 50, 40,
				0, 4, 5, null, null);
		Earthling fighter2 = new Earthling("fighter2", 1, 1, 10, 1250, 50, 40,
				10, 4, 5, null, null);
		ArrayList<PlayableFighter> fighters = new ArrayList<PlayableFighter>();

		fighters.add(fighter1);
		fighters.add(fighter2);

		Player player = new Player("player");
		player.setFighters(fighters);
		player.setActiveFighter(fighter1);

		boolean thrown = false;
		try {
			player.upgradeFighter(fighter1, 'P');
		} catch (NotEnoughAbilityPointsException e) {
			thrown = true;
			if (e.getMessage() == null)
				fail("NotEnoughAbilityPointsException should be initialized with a descriptive customized message");

		}
		assertTrue(
				"NotEnoughAbilityPointsException should be thrown if the user tries to upgrade a fighter who has zero abilityPoints",
				thrown);

		thrown = false;
		try {
			player.upgradeFighter(fighter2, 'P');
		} catch (NotEnoughAbilityPointsException e) {
			thrown = true;
		}
		assertFalse(
				"NotEnoughAbilityPointsException should not be thrown if the user tries to upgrade a fighter who has enough abilityPoints",
				thrown);

	}

	@Test(timeout = 1000)
	public void testGameLoadFunctionality() throws Exception {
		Game g = initializeGame();
		String r = (int) (Math.random() * 1000) + "";
		g.save(r);
		Game x = new Game();
		x.load(r);
		ArrayList<String> res = inspectGame(x);
		File f = new File(r);
		f.delete();
		if (res.size() != 0)
			fail("the following instance variables have not been loaded correctly after trying to load a game object :"
					+ join(res));
	}

	@Test(timeout = 1000)
	public void testGameAsPlayerListenerAfterLoad() throws Exception {
		Game g = initializeGame();
		String r = (int) (Math.random() * 1000) + "";
		g.save(r);
		Game x = new Game();
		x.load(r);
		File ff = new File(r);
		ff.delete();
		Field[] fields = Player.class.getDeclaredFields();
		int index = -1;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getType().equals(PlayerListener.class))
				index = i;
		}
		if (index == -1)
			fail("Player class should have an instance variable of type PlayerListener");
		else {
			Field f = Player.class.getDeclaredFields()[index];
			f.setAccessible(true);
			PlayerListener l = (PlayerListener) f.get(x.getPlayer());
			assertTrue(
					"After loading a game , you should make this instance of the game the listener of the player object it has",
					l == x);
		}
	}

	@Test(timeout = 1000)
	public void testGameAsWorldListenerAfterLoad() throws Exception {
		Game g = initializeGame();
		String r = (int) (Math.random() * 1000) + "";
		g.save(r);
		Game x = new Game();
		x.load(r);
		File ff = new File(r);
		ff.delete();
		Field[] fields = World.class.getDeclaredFields();
		int index = -1;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getType().equals(WorldListener.class))
				index = i;
		}
		if (index == -1)
			fail("World class should have an instance variable of type WorldListener");
		else {
			Field f = World.class.getDeclaredFields()[index];
			f.setAccessible(true);
			WorldListener w = (WorldListener) f.get(x.getWorld());
			assertTrue(
					"After loading a game , you should make this instance of the game the listener of the world object it has",
					w == x);
		}
	}

	@Test(timeout = 1000)
	public void testLoadAfterActiveFighterDeathWithSave() throws Exception {
		Game g = new Game();
		ArrayList<Attack> attacks = new ArrayList<Attack>();
		attacks.add(new SuperAttack("Death Beam", 250));
		Field f = Game.class.getDeclaredField("attacks");
		f.setAccessible(true);
		f.set(g, attacks);
		g.save("s1");
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon("dodo", null, null, 4, 8));
		f = Game.class.getDeclaredField("dragons");
		f.setAccessible(true);
		f.set(g, dragons);
		g.save("s2");
		g = initializeGame();
		g.save("s3");
		NonPlayableFighter strong = new NonPlayableFighter("Goku", 10, 3000,
				350, 100, 5, 6, true, null, null);
		Frieza fr = new Frieza("Frieza");
		Battle b = new Battle(fr, strong);
		b.setListener(g);
		b.start();
		b.attack(new PhysicalAttack());
		fr.setHealthPoints(0);
		b.attack(new PhysicalAttack());

		ArrayList<String> res = inspectGame(g);
		File fi = new File("s1");
		fi.delete();
		fi = new File("s2");
		fi.delete();
		fi = new File("s3");
		fi.delete();
		assertEquals(
				"If the player's active fighter has died in a battle and the player already saved before , the game should be loaded to the last saved state ",
				0, res.size());
	}

	@Test(timeout = 1000)
	public void testGameSaveFunctionality() throws Exception {
		Game g = initializeGame();
		String r = (int) (Math.random() * 1000) + "";
		g.save(r);
		Game x = deserialize(r);
		File f = new File(r);
		f.delete();
		ArrayList<String> res = inspectGame(x);
		if (res.size() != 0)
			fail("the following instance variables have not been saved correctly after trying to save a game object :"
					+ join(res));

	}

	@Test(timeout = 1000)
	public void testLoadAfterActiveFighterDeathWithoutSave() throws Exception {
		Game g = new Game();
		Cell[][] before = g.getWorld().getMap();
		NonPlayableFighter strong = new NonPlayableFighter("Goku", 10, 3000,
				350, 100, 5, 6, true, null, null);
		Frieza fr = new Frieza("Frieza");
		Battle b = new Battle(fr, strong);
		b.setListener(g);
		b.start();
		b.attack(new PhysicalAttack());
		fr.setHealthPoints(0);
		b.attack(new PhysicalAttack());
		Cell[][] after = g.getWorld().getMap();
		assertFalse(
				"If the player's active fighter died in a battle and the player has not saved the game before , a new world should be generated",
				TwoMapsEqual(before, after));
		boolean p = g.getWorld().getPlayerRow() == 9
				&& g.getWorld().getPlayerColumn() == 9;
		assertTrue(
				"The generated map after the death of the player's active fighter in a battle in case the player did not save before should have the player location at cell 9,9",
				p);
		ArrayList<String> res = validateMap(after);
		assertEquals(
				"The generated map after the death of the player's active fighter in a battle in case the player did not save before has the following invalid attributes :"
						+ join(res), 0, res.size());
		Field[] fields = World.class.getDeclaredFields();
		int index = -1;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getType().equals(WorldListener.class))
				index = i;
		}
		if (index == -1)
			fail("Player class should have an instance variable of type PlayerListener");
		else {
			Field f = World.class.getDeclaredFields()[index];
			f.setAccessible(true);
			WorldListener l = (WorldListener) f.get(g.getWorld());
			assertTrue(
					"After generating a new World in case the player's active fighter has died in a battle and the player did not save before , the game should listen to the new generated world",
					l == g);
		}
	}

	public static boolean TwoMapsEqual(Cell[][] map1, Cell[][] map2) {
		int different = 0;

		for (int i = 0; i < map1.length; i++) {
			for (int j = 0; j < map1[i].length; j++) {

				if (!map1[i][j].getClass().equals(map2[i][j].getClass()))
					different++;
				else {

					if (map1[i][j].getClass().equals(EmptyCell.class)) {
						different++;
						continue;
					}

					if (map1[i][j].getClass().equals(FoeCell.class)
							&& !(((FoeCell) map1[i][j]).getFoe().getName()
									.equals(((FoeCell) map2[i][j]).getFoe()
											.getName())))
						different++;

					else if (map1[i][j].getClass()
							.equals(CollectibleCell.class)
							&& (((CollectibleCell) map1[i][j]).getCollectible() != ((CollectibleCell) map2[i][j])
									.getCollectible()))
						different++;
				}
			}

		}

		if (different >= 90)
			return false;

		return true;

	}

	public static ArrayList<String> validateMap(Cell[][] map) {
		ArrayList<String> res = new ArrayList<String>();
		int wfc = 0;
		int dbc = 0;
		int sbc = 0;
		int sfc = 0;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] instanceof FoeCell
						&& ((FoeCell) map[i][j]).getFoe().isStrong())
					sfc++;
				else if (map[i][j] instanceof FoeCell
						&& !((FoeCell) map[i][j]).getFoe().isStrong())
					wfc++;
				else if (map[i][j] instanceof CollectibleCell
						&& ((CollectibleCell) map[i][j]).getCollectible() == Collectible.DRAGON_BALL)
					dbc++;
				else if (map[i][j] instanceof CollectibleCell
						&& ((CollectibleCell) map[i][j]).getCollectible() == Collectible.SENZU_BEAN)
					sbc++;
			}
		}
		if (!(map[0][0] instanceof FoeCell)
				|| ((FoeCell) map[0][0]).getFoe().isStrong() == false)
			res.add("Strong Foe not in cell 0,0");
		else if (wfc != 15)
			res.add("Weak Foes count");
		else if (sfc != 1)
			res.add("Strong Foes count");
		else if (dbc != 1)
			res.add("Dragon Balls count");
		else if (sbc < 3 || sbc > 5)
			res.add("Senzu Beans count");
		return res;
	}

	private static Game initializeGame() throws Exception {
		Player p = new Player("7amada");
		GameController c = new GameController("controller");
		Game g = new Game();
		g.setListener(c);
		ArrayList<Attack> attacks = new ArrayList<Attack>();
		attacks.add(new SuperAttack("Kamehamehaha", 250));
		ArrayList<Dragon> dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon("Shenron", null, null, 5, 3));
		ArrayList<NonPlayableFighter> weakFoes = new ArrayList<NonPlayableFighter>();
		weakFoes.add(new NonPlayableFighter("CellJunior", 5, 1000, 100, 200, 3,
				4, false, null, null));
		ArrayList<NonPlayableFighter> strongFoes = new ArrayList<NonPlayableFighter>();
		strongFoes.add(new NonPlayableFighter("Frieza", 10, 3000, 200, 300, 5,
				6, true, null, null));

		Field f = Game.class.getDeclaredField("weakFoes");
		f.setAccessible(true);
		f.set(g, weakFoes);
		f = Game.class.getDeclaredField("strongFoes");
		f.setAccessible(true);
		f.set(g, strongFoes);
		f = Game.class.getDeclaredField("attacks");
		f.setAccessible(true);
		f.set(g, attacks);
		f = Game.class.getDeclaredField("dragons");
		f.setAccessible(true);
		f.set(g, dragons);
		f = Game.class.getDeclaredField("player");
		f.setAccessible(true);
		f.set(g, p);
		f = Game.class.getDeclaredField("state");
		f.setAccessible(true);
		f.set(g, GameState.DRAGON);

		f = Game.class.getDeclaredField("world");
		f.setAccessible(true);
		World w = (World) f.get(g);

		Cell[][] myMap = new Cell[10][10];
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				myMap[i][j] = new EmptyCell();

		myMap[0][0] = new FoeCell(new NonPlayableFighter("Frieza", 10, 3000,
				200, 300, 5, 6, true, null, null));
		myMap[5][5] = new CollectibleCell(Collectible.SENZU_BEAN);
		myMap[1][3] = new FoeCell(new NonPlayableFighter("CellJunior", 5, 1000,
				100, 200, 3, 4, false, null, null));

		f = World.class.getDeclaredField("map");
		f.setAccessible(true);
		f.set(w, myMap);

		f = World.class.getDeclaredField("playerRow");
		f.setAccessible(true);
		f.set(w, 5);
		f = World.class.getDeclaredField("playerColumn");
		f.setAccessible(true);
		f.set(w, 7);

		return g;
	}

	private static Game deserialize(String name) throws Exception {
		FileInputStream fileIn = new FileInputStream(name);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		Game g = (Game) in.readObject();
		in.close();
		fileIn.close();
		return g;
	}

	private static ArrayList<String> inspectGame(Game g) throws Exception{
		ArrayList<String> res = new ArrayList<String>();
		Field[] fields = Game.class.getDeclaredFields();
		int index = -1;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getType().equals(GameListener.class))
				index = i;
		}
		Field f = fields[index];
		f.setAccessible(true);
		GameController c = (GameController) f.get(g);
		if (g.getWorld().getPlayerRow() != 5)
			res.add("World's playerRow");
		else if (g.getWorld().getPlayerColumn() != 7)
			res.add("World's playerColumn");
		else if (g.getWeakFoes() == null
				|| !g.getWeakFoes().get(0).getName().equals("CellJunior")
				|| g.getWeakFoes().get(0).getLevel() != 5
				|| g.getWeakFoes().get(0).getSuperAttacks() != null
				|| g.getWeakFoes().get(0).getUltimateAttacks() != null)
			res.add("Game's weakFoes");
		else if (g.getStrongFoes() == null
				|| !g.getStrongFoes().get(0).getName().equals("Frieza")
				|| g.getStrongFoes().get(0).getLevel() != 10
				|| g.getStrongFoes().get(0).getSuperAttacks() != null
				|| g.getStrongFoes().get(0).getUltimateAttacks() != null)
			res.add("Game's strongFoes");
		else if (g.getAttacks() == null
				|| !g.getAttacks().get(0).getName().equals("Kamehamehaha")
				|| g.getAttacks().get(0).getDamage() != 250)
			res.add("Game's attacks");
		else if (g.getDragons() == null
				|| !g.getDragons().get(0).getName().equals("Shenron")
				|| g.getDragons().get(0).getSuperAttacks() != null
				|| g.getDragons().get(0).getUltimateAttacks() != null
				|| g.getDragons().get(0).getSenzuBeans() != 5
				|| g.getDragons().get(0).getAbilityPoints() != 3)
			res.add("Game's dragons");
		else if (g.getPlayer() == null
				|| !g.getPlayer().getName().equals("7amada"))
			res.add("Game's player");
		else if (g.getState() == null || g.getState() != GameState.DRAGON)
			res.add("Game's state");
		else if (index == -1)
			fail("the Game class should have an instance variable of type GameListener");
		else if (c == null || !c.name.equals("controller"))
			res.add("Game's gameListener");
		return res;
	}

	private static String join(ArrayList<String> in) {
		String res = " ";
		for (int i = 0; i < in.size(); i++) {
			res += in.get(i) + ",";
		}
		res = res.substring(0, res.length() - 1);
		return res;

	}

	public static boolean containsMethodName(Method[] methods, String name) {
		for (Method method : methods) {
			if (method.getName().equals(name))
				return true;
		}
		return false;
	}

	private static void invokeSetter(Object mainObject, Class variableClass,
			Object variableObject) throws Exception {

		int index = classContainsFieldAtIndex(mainObject.getClass(),
				variableClass);
		if (index != -1) {
			Field f = mainObject.getClass().getDeclaredFields()[index];
			String n = f.getName().toUpperCase();
			String methodName = "set" + n.charAt(0) + f.getName().substring(1);

			Method setter = mainObject.getClass().getMethod(methodName,
					variableClass);
			try {
				setter.invoke(mainObject, variableObject);
			} catch (Exception e) {
				fail("The class: " + mainObject.getClass()
						+ " should have a setter method for the listener");
			}
		}
	}

	private static int classContainsFieldAtIndex(Class c, Class contained) {
		Field[] fields = c.getDeclaredFields();
		int i = -1;
		for (int j = 0; j < fields.length; j++) {
			if (fields[j].getType().equals(contained)) {
				i = j;
				break;
			}
		}
		return i;
	}

	public static void clearWorld(Cell[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new EmptyCell();
			}
		}
	}

	public static void executeLoadDragons_InCorrectFile_MissingField(Object o,
			Class c) throws Exception {
		Method method = c.getDeclaredMethod("loadDragons",
				new Class[] { String.class });
		method.setAccessible(true);

		PrintWriter dragonsWriter = new PrintWriter("DragonsTest.csv");
		dragonsWriter.println("Dragon1,5,7");
		dragonsWriter.println("Demon Wave,Nova Star");
		dragonsWriter.println("Weekend,Red Magma,Final Flash");
		dragonsWriter.println("10,20");
		dragonsWriter
				.println("Majin Kamehameha,Perfect Special Beam Cannon,Ice Ray");
		dragonsWriter.println("Spirit Sword,Genocide Blast");
		dragonsWriter.close();

		method.invoke(o, "DragonsTest.csv");
	}

	public static void executeLoadDragons_CorrectFile_MissingField(Object o,
			Class c) throws Exception {
		Method method = c.getDeclaredMethod("loadDragons",
				new Class[] { String.class });
		method.setAccessible(true);

		PrintWriter dragonsWriter = new PrintWriter("DragonsTest.csv");
		dragonsWriter.println("Dragon1,5,7");
		dragonsWriter.println("Demon Wave,Nova Star");
		dragonsWriter.println("Weekend,Red Magma,Final Flash");
		dragonsWriter.println("Dragon2,10,20");
		dragonsWriter
				.println("Majin Kamehameha,Perfect Special Beam Cannon,Ice Ray");
		dragonsWriter.println("Spirit Sword,Genocide Blast");
		dragonsWriter.close();

		method.invoke(o, "DragonsTest.csv");
	}

}

class GameController implements GameListener,Serializable {
	public String name;

	public GameController(String n) {
		name = n;
	}

	@Override
	public void onDragonCalled(Dragon dragon) {

	}

	@Override
	public void onCollectibleFound(Collectible collectible) {

	}

	@Override
	public void onBattleEvent(BattleEvent e) {

	}

}
