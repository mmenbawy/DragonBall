package dragonball.tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
import dragonball.model.attack.SuperSaiyan;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.battle.Battle;
import dragonball.model.cell.Cell;
import dragonball.model.cell.EmptyCell;
import dragonball.model.character.fighter.Earthling;
import dragonball.model.character.fighter.Fighter;
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
import dragonball.model.exceptions.NotASaiyanException;
import dragonball.model.exceptions.NotEnoughKiException;
import dragonball.model.exceptions.NotEnoughResourcesException;
import dragonball.model.exceptions.NotEnoughSenzuBeansException;
import dragonball.model.exceptions.UnknownAttackTypeException;
import dragonball.model.game.Game;
import dragonball.model.player.Player;
import dragonball.model.world.World;

public class M3PublicTests {
	private int thrown = 0;

	@Test(timeout = 1000)
	public void testLoadMethodStructure() throws Exception {
		Method[] methods = Game.class.getDeclaredMethods();
		assertTrue("Game Class should have \"load\" method",
				containsMethodName(methods, "load"));
		Method m;
		try {
			m = Game.class.getDeclaredMethod("load", String.class);
			assertTrue(
					"incorrect return type for \"load\" method in Game Class.",
					m.getReturnType().equals(Void.TYPE));
		} catch (Exception e) {
			fail("Missing \"load\" method in Game Class which takes a String as an input parameter");
		}
	}

	@Test(timeout = 1000)
	public void testInvalidFormatExceptionExtendsIOException() throws Exception {
		assertEquals(
				"InvalidFormatException class should extend predefined IOException",
				IOException.class, InvalidFormatException.class.getSuperclass());
	}

	@Test(timeout = 1000)
	public void testInvalidFormatExceptionIsAbstract() throws Exception {
		assertTrue(
				"InvalidFormatException class should be an abstract class",
				Modifier.isAbstract(InvalidFormatException.class.getModifiers()));
	}

	@Test(timeout = 1000)
	public void testInvalidFormatExceptionClassVariables() throws Exception {
		Field f = null;
		boolean thrown = false;
		try {
			f = InvalidFormatException.class.getDeclaredField("sourceFile");
		} catch (NoSuchFieldException e) {
			thrown = true;
		}
		assertFalse(
				"there should be \"sourceFile\" instance variable in class InvalidFormatException",
				thrown);
		assertEquals(
				"\"sourceFile\" instance variable in class InvalidFormatException should be of type String",
				f.getType(), String.class);

		thrown = false;
		try {
			f = InvalidFormatException.class.getDeclaredField("sourceLine");
		} catch (NoSuchFieldException e) {
			thrown = true;
		}
		assertFalse(
				"there should be \"sourceLine\" instance variable in class InvalidFormatException",
				thrown);
		assertEquals(
				"\"sourceLine\" instance variable in class InvalidFormatException should be of type int",
				f.getType(), int.class);
	}

	@Test(timeout = 1000)
	public void testInvalidFormatExceptionClassVariablesAccessibility()
			throws Exception {

		Field f = InvalidFormatException.class.getDeclaredField("sourceFile");
		assertEquals(
				"\"sourceFile\" instance variable in class InvalidFormatException should not be accessed outside that class",
				2, f.getModifiers());

		f = InvalidFormatException.class.getDeclaredField("sourceFile");
		assertEquals(
				"\"sourceLine\" instance variable in class InvalidFormatException should not be accessed outside that class",
				2, f.getModifiers());

	}

	@Test(timeout = 1000)
	public void testInvalidFormatExceptionClassREADVariables() throws Exception {
		Method[] methods = InvalidFormatException.class.getDeclaredMethods();

		assertTrue(
				"The \"sourceFile\" instance variable in class InvalidFormatException is a READ variable.",
				containsMethodName(methods, "getSourceFile"));

		assertTrue(
				"The \"sourceLine\" instance variable in class InvalidFormatException is a READ variable.",
				containsMethodName(methods, "getSourceLine"));

		try {
			Method m = InvalidFormatException.class
					.getDeclaredMethod("getSourceFile");
			assertTrue(
					"incorrect return type for \"getSourceFile\" method in InvalidFormatException class.",
					m.getReturnType().equals(String.class));
		} catch (Exception e) {
			fail("Missing \"getSourceFile\" method in InvalidFormatException class which takes no input parameters.");
		}

		try {
			Method m = InvalidFormatException.class
					.getDeclaredMethod("getSourceLine");
			assertTrue(
					"incorrect return type for \"getSourceLine\" method in InvalidFormatException class.",
					m.getReturnType().equals(int.class));
		} catch (Exception e) {
			fail("Missing \"getSourceLine\" method in InvalidFormatException class which takes no input parameters.");
		}

		assertFalse(
				"The \"sourceFile\" instance variable in class InvalidFormatException is a READ ONLY variable.",
				containsMethodName(methods, "setSourceFile"));

		assertFalse(
				"The \"sourceLine\" instance variable in class InvalidFormatException is a READ ONLY variable.",
				containsMethodName(methods, "setSourceLine"));

	}

	@Test(timeout = 1000)
	public void testInvalidFormatExceptionFirstConstructor() throws Exception {
		Class<InvalidFormatException> aClass = InvalidFormatException.class;
		boolean thrown = false;
		try {
			aClass.getConstructor(new Class[] { String.class, int.class });
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with 2 parameters in InvalidFormatException class.",
				thrown);
	}

	@Test(timeout = 1000)
	public void testInvalidFormatExceptionSecondConstructor() throws Exception {
		Class<InvalidFormatException> aClass = InvalidFormatException.class;
		boolean thrown = false;
		try {
			aClass.getConstructor(new Class[] { String.class, String.class,
					int.class });
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with 3 parameters in InvalidFormatException class.",
				thrown);
	}

	@Test(timeout = 1000)
	public void testMissingFieldExceptionExtendsInvalidFormatException()
			throws Exception {
		assertEquals(
				"MissingFieldException class should extend predefined InvalidFormatException",
				InvalidFormatException.class,
				MissingFieldException.class.getSuperclass());
	}

	@Test(timeout = 1000)
	public void testMissingFieldExceptionClassVariables() throws Exception {
		Field f = null;
		boolean thrown = false;
		try {
			f = MissingFieldException.class.getDeclaredField("missingFields");
		} catch (NoSuchFieldException e) {
			thrown = true;
		}
		assertFalse(
				"there should be \"missingFields\" instance variable in class MissingFieldException",
				thrown);
		assertEquals(
				"\"missingFields\" instance variable in class MissingFieldException should be of type int",
				f.getType(), int.class);
	}

	@Test(timeout = 1000)
	public void testMissingFieldExceptionClassVariablesAccessibility()
			throws Exception {
		Field f = MissingFieldException.class.getDeclaredField("missingFields");
		assertEquals(
				"\"missingFields\" instance variable in class MissingFieldException should not be accessed outside that class",
				2, f.getModifiers());
	}

	@Test(timeout = 1000)
	public void testMissingFieldExceptionClassREADVariables() throws Exception {
		Method[] methods = MissingFieldException.class.getDeclaredMethods();

		assertTrue(
				"The \"missingFields\" instance variable in class MissingFieldException is a READ variable.",
				containsMethodName(methods, "getMissingFields"));

		try {
			Method m = MissingFieldException.class
					.getDeclaredMethod("getMissingFields");
			assertTrue(
					"incorrect return type for \"getMissingFields\" method in MissingFieldException class.",
					m.getReturnType().equals(int.class));
		} catch (Exception e) {
			fail("Missing \"getMissingFields\" method in MissingFieldException class which takes no input parameters.");
		}

		assertFalse(
				"The \"missingFields\" instance variable in class MissingFieldException is a READ ONLY variable.",
				containsMethodName(methods, "setMissingFields"));
	}

	@Test(timeout = 1000)
	public void testMissingFieldExceptionFirstConstructor() throws Exception {
		Class<MissingFieldException> aClass = MissingFieldException.class;
		boolean thrown = false;
		try {
			aClass.getConstructor(new Class[] { String.class, int.class,
					int.class });
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with 3 parameters in MissingFieldException class.",
				thrown);
	}

	@Test(timeout = 1000)
	public void testMissingFieldExceptionSecondConstructor() throws Exception {
		Class<MissingFieldException> aClass = MissingFieldException.class;
		boolean thrown = false;
		try {
			aClass.getConstructor(new Class[] { String.class, String.class,
					int.class, int.class });
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with 4 parameters in MissingFieldException class.",
				thrown);
	}

	@Test(timeout = 1000)
	public void testMissingFieldFirstConstructorInitialization()
			throws Exception {

		MissingFieldException e = new MissingFieldException("sourceFile", 1, 2);
		assertEquals(
				"The constructor of MissingFieldException class should initialize the sourceFile instance variable correctly by calling super.",
				"sourceFile", e.getSourceFile());

		assertEquals(
				"The constructor of MissingFieldException class should initialize the sourceLine instance variable correctly by calling super.",
				1, e.getSourceLine());

		assertEquals(
				"The constructor of MissingFieldException class should initialize the unknownType instance variable correctly.",
				2, e.getMissingFields());
	}

	@Test(timeout = 1000)
	public void testMissingFieldSecondConstructorInitialization()
			throws Exception {

		MissingFieldException e = new MissingFieldException("Message",
				"sourceFile", 1, 2);

		assertTrue(
				"The constructor of MissingFieldException class should initialize the message instance variable correctly by calling super.",
				e.getMessage().contains("Message"));

		assertEquals(
				"The constructor of MissingFieldException class should initialize the sourceFile instance variable correctly by calling super.",
				"sourceFile", e.getSourceFile());

		assertEquals(
				"The constructor of MissingFieldException class should initialize the sourceLine instance variable correctly by calling super.",
				1, e.getSourceLine());

		assertEquals(
				"The constructor of MissingFieldException class should initialize the unknownType instance variable correctly.",
				2, e.getMissingFields());
	}

	@Test(timeout = 1000)
	public void testDragonBallExceptionExtendsException() throws Exception {
		assertEquals("DragonBallException class should extend Exception",
				Exception.class, DragonBallException.class.getSuperclass());
	}

	@Test(timeout = 1000)
	public void testDragonBallExceptionIsAbstract() throws Exception {
		assertTrue(
				"DragonBallException class should be an abstract class",
				Modifier.isAbstract(InvalidFormatException.class.getModifiers()));
	}

	@Test(timeout = 1000)
	public void testDragonBallExceptionFirstConstructor() throws Exception {
		Class<DragonBallException> aClass = DragonBallException.class;
		boolean thrown = false;
		try {
			aClass.getConstructor(new Class[] {});
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with no parameters in DragonBallException class.",
				thrown);
	}

	@Test(timeout = 1000)
	public void testDragonBallExceptionSecondConstructor() throws Exception {
		Class<DragonBallException> aClass = DragonBallException.class;
		boolean thrown = false;
		try {
			aClass.getConstructor(new Class[] { String.class });
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with 1 parameter in DragonBallException class.",
				thrown);
	}

	@Test(timeout = 1000)
	public void testNotEnoughSenzuBeansExtendsNotEnoughResources()
			throws Exception {
		assertEquals(
				"NotEnoughSenzuBeansException class should extend NotEnoughResourcesException",
				NotEnoughResourcesException.class,
				NotEnoughSenzuBeansException.class.getSuperclass());
	}

	@Test(timeout = 1000)
	public void testNotEnoughSenzuBeansExceptionConstructor() throws Exception {
		Class<NotEnoughSenzuBeansException> aClass = NotEnoughSenzuBeansException.class;
		boolean thrown = false;
		try {
			aClass.getConstructor(new Class[] {});
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with no parameters in NotEnoughSenzuBeansException class.",
				thrown);
	}

	@Test(timeout = 1000)
	public void testNotEnoughKiExceptionExtendsNotEnoughResourcesException()
			throws Exception {
		assertEquals(
				"NotEnoughKiException class should extend predefined NotEnoughResourcesException",
				NotEnoughResourcesException.class,
				NotEnoughKiException.class.getSuperclass());
	}

	@Test(timeout = 1000)
	public void testNotEnoughKiExceptionClassVariables() throws Exception {
		Field f = null;
		boolean thrown = false;
		try {
			f = NotEnoughKiException.class.getDeclaredField("availableKi");
		} catch (NoSuchFieldException e) {
			thrown = true;
		}
		assertFalse(
				"there should be \"availableKi\" instance variable in class NotEnoughKiException",
				thrown);
		assertEquals(
				"\"availableKi\" instance variable in class NotEnoughKiException should be of type int",
				f.getType(), int.class);
		thrown = false;
		try {
			f = NotEnoughKiException.class.getDeclaredField("requiredKi");
		} catch (NoSuchFieldException e) {
			thrown = true;
		}
		assertFalse(
				"there should be \"requiredKi\" instance variable in class NotEnoughKiException",
				thrown);
		assertEquals(
				"\"requiredKi\" instance variable in class NotEnoughKiException should be of type int",
				f.getType(), int.class);
	}

	@Test(timeout = 1000)
	public void testNotEnoughKiExceptionClassVariablesAccessibility()
			throws Exception {
		Field f = NotEnoughKiException.class.getDeclaredField("availableKi");
		assertEquals(
				"\"availableKi\" instance variable in class NotEnoughKiException should not be accessed outside that class",
				2, f.getModifiers());
		f = NotEnoughKiException.class.getDeclaredField("requiredKi");
		assertEquals(
				"\"requiredKi\" instance variable in class NotEnoughKiException should not be accessed outside that class",
				2, f.getModifiers());
	}

	@Test(timeout = 1000)
	public void testNotEnoughKiExceptionClassREADVariables() throws Exception {
		Method[] methods = NotEnoughKiException.class.getDeclaredMethods();

		assertTrue(
				"The \"availableKi\" instance variable in class NotEnoughKiException is a READ variable.",
				containsMethodName(methods, "getAvailableKi"));
		assertTrue(
				"The \"requiredKi\" instance variable in class NotEnoughKiException is a READ variable.",
				containsMethodName(methods, "getRequiredKi"));

		try {
			Method m = NotEnoughKiException.class
					.getDeclaredMethod("getRequiredKi");
			assertTrue(
					"incorrect return type for \"getRequiredKi\" method in NotEnoughKiException class.",
					m.getReturnType().equals(int.class));
		} catch (Exception e) {
			fail("Missing \"getRequiredKi\" method in NotEnoughKiException class which takes no input parameters.");
		}

		assertFalse(
				"The \"availableKi\" instance variable in class NotEnoughKiException is a READ ONLY variable.",
				containsMethodName(methods, "setAvailableKi"));
		assertFalse(
				"The \"requiredKi\" instance variable in class NotEnoughKiException is a READ ONLY variable.",
				containsMethodName(methods, "setRequiredKi"));
	}

	@Test(timeout = 1000)
	public void testNotEnoughKiExceptionConstructor() throws Exception {
		Class<NotEnoughKiException> aClass = NotEnoughKiException.class;
		boolean thrown = false;
		try {
			aClass.getConstructor(new Class[] {
					int.class, int.class });
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with 2 parameters in NotEnoughKiException class.",
				thrown);
	}

	@Test(timeout = 1000)
	public void testNotEnoughKiConstructorInitialization() throws Exception {

		NotEnoughKiException e = new NotEnoughKiException(1, 2);
		assertEquals(
				"The constructor of NotEnoughKiException class should initialize the availableKi instance variable correctly.",
				2, e.getAvailableKi());

		assertEquals(
				"The constructor of NotEnoughKiException class should initialize the requiredKi instance variable correctly.",
				1, e.getRequiredKi());
	}

	@Test(timeout = 1000)
	public void testNotASaiyanExceptionExtendsInvalidAssignAttackException()
			throws Exception {
		assertEquals(
				"NotASaiyanException class should extend InvalidAssignAttackException",
				InvalidAssignAttackException.class,
				NotASaiyanException.class.getSuperclass());
	}

	@Test(timeout = 1000)
	public void testNotASaiyanExceptionConstructor() throws Exception {
		Class<NotASaiyanException> aClass = NotASaiyanException.class;
		boolean thrown = false;
		try {
			aClass.getConstructor(new Class[] {});
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with no parameters in NotASaiyanException class.",
				thrown);
	}

	@Test(timeout = 1000)
	public void testMaximumAttacksLearnedExtendsNotEnoughResources()
			throws Exception {
		assertEquals(
				"MaximumAttacksLearnedException class should extend InvalidAssignAttackException",
				InvalidAssignAttackException.class,
				MaximumAttacksLearnedException.class.getSuperclass());
	}

	@Test(timeout = 1000)
	public void testMaximumAttacksLearnedExceptionConstructor()
			throws Exception {
		Class<MaximumAttacksLearnedException> aClass = MaximumAttacksLearnedException.class;
		boolean thrown = false;
		try {
			aClass.getConstructor(new Class[] {});
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with no parameters in MaximumAttacksLearnedException class.",
				thrown);
	}

	@Test(timeout = 1000)
	public void testMoveUpOutOfBoundaries() throws Exception {
		Game g = new Game();
		World w = g.getWorld();
		clearWorld(w.getMap());

		Field f;

		f = w.getClass().getDeclaredField("playerRow");
		f.setAccessible(true);
		f.set(w, 1);

		f = w.getClass().getDeclaredField("playerColumn");
		f.setAccessible(true);
		f.set(w, 8);

		try {
			g.getWorld().moveUp();
		} catch (MapIndexOutOfBoundsException e) {
			fail("No exception should be thrown when trying to move the player Up in the map boundaries.");
		}
		try {
			g.getWorld().moveUp();
		} catch (MapIndexOutOfBoundsException e) {
			assertTrue(
					"MoveUp Method in the World class should include the row and column numbers of the fighter when attempting an invalid move in the message of the Exception thrown ",
					e.getMessage().contains("0")
							&& e.getMessage().contains("8"));
			return;
		}
		fail("moveUp method in class World should throw the appropriate exception when moving out of boundaries");

	}

	@Test(timeout = 1000)
	public void testMoveRightOutOfBoundaries() throws Exception {
		Game g = new Game();
		World w = g.getWorld();
		clearWorld(w.getMap());
		Field f;
		f = w.getClass().getDeclaredField("playerRow");
		f.setAccessible(true);
		f.set(w, 3);

		f = w.getClass().getDeclaredField("playerColumn");
		f.setAccessible(true);
		f.set(w, 8);

		try {
			g.getWorld().moveRight();
		} catch (MapIndexOutOfBoundsException e) {
			fail("No exception should be thrown when trying to move the player right in the map boundaries.");
		}
		try {
			g.getWorld().moveRight();
		} catch (MapIndexOutOfBoundsException e) {
			assertTrue(
					"MoveRight Method in the World class should include the row and column numbers of the fighter when attempting an invalid move in the message of the Exception thrown ",
					e.getMessage().contains("3")
							&& e.getMessage().contains("9"));

			return;
		}
		fail("moveRight method in class World should throw the appropriate exception when moving out of boundaries");

	}

	@Test(timeout = 1000)
	public void testUltimateAssignAttackMethod_Throws_MaximumAttacksLearnedException()
			throws Exception, MaximumAttacksLearnedException,
			DuplicateAttackException {
		Earthling fighter1 = new Earthling("fighter1");
		ArrayList<UltimateAttack> ultimateAttacks1 = new ArrayList<UltimateAttack>();
		UltimateAttack u1 = new UltimateAttack("Ultimate1", 50);
		ultimateAttacks1.add(u1);
		fighter1.setUltimateAttacks(ultimateAttacks1);

		ArrayList<UltimateAttack> ultimateAttacks2 = new ArrayList<UltimateAttack>();
		UltimateAttack u3 = new UltimateAttack("Ultimate3", 50);
		UltimateAttack u4 = new UltimateAttack("Ultimate4", 50);
		ultimateAttacks2.add(u3);
		ultimateAttacks2.add(u4);
		Earthling fighter2 = new Earthling("fighter2");
		fighter2.setUltimateAttacks(ultimateAttacks2);

		ArrayList<PlayableFighter> fighters = new ArrayList<PlayableFighter>();
		fighters.add(fighter1);
		fighters.add(fighter2);

		Player player = new Player("player");
		player.setFighters(fighters);

		UltimateAttack ultimateAttack = new UltimateAttack("Ultimate", 50);
		try {
			player.assignAttack(fighter1, ultimateAttack, u1);
		} catch (MaximumAttacksLearnedException e) {
			fail("assignAttack method in class Player should add the new Ultimate Attack to the list of UltimateAttacks of the input fighter; No Exception should be thrown");
		}

		UltimateAttack ultimateAttack2 = new UltimateAttack("Ultimate2", 50);
		try {
			player.assignAttack(fighter1, ultimateAttack2, null);
		} catch (MaximumAttacksLearnedException e) {
			fail("assignAttack method in class Player should add the new Ultimate Attack to the list of ultimateAttacks of the input fighter; No Exception should be thrown");
		}

		try {
			player.assignAttack(fighter2, ultimateAttack, null);
		} catch (MaximumAttacksLearnedException e) {
			return;
		}
		fail("assignAttack method in class Player should throw the appropriate Exceptions if the list of ultimate attacks of the input fighter is full");

		try {
			player.assignAttack(fighter2, ultimateAttack, u3);
		} catch (MaximumAttacksLearnedException e) {
			return;
		}
		fail("assignAttack method in class Player should throw the appropriate Exceptions when trying to assign an ultimate attack to the fighter while he/she already has the maximum possible number of attacks assigned");

	}

	@Test(timeout = 1000)
	public void testSuperAssignAttackMethod_Throws_DuplicateAttackException()
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

		ArrayList<PlayableFighter> fighters = new ArrayList<PlayableFighter>();
		fighters.add(fighter1);

		Player player = new Player("player");
		player.setFighters(fighters);

		try {
			player.assignAttack(fighter1, s1, s2);
		} catch (DuplicateAttackException e) {
			Attack learned = e.getAttack();
			assertEquals("", s1, learned);
			return;
		}

		fail("assignAttack method in class Player should throw the appropriate Exceptions when trying to assign an attack to a fighter, while it already exists in his/her list of assigned attacks");
	}

	@Test(timeout = 1000)
	public void testLoadAttacksMethod_Throws_MissingFieldException()
			throws Exception {
		try {
			Game g = new Game();
			g.getAttacks().clear();
			executeLoadAttacks_InCorrectFile_MissingField(g, Game.class);
		} catch (Exception e) {
			assertTrue(
					"Load Attacks Method in the Game class should throw the appropriate Exception when a field is missing in the file",
					e.getCause() instanceof MissingFieldException);
			assertTrue(
					"Load Attacks Method in the Game class should include the File name in the message of the Exception thrown when a field is missing in the file ",
					((MissingFieldException) e.getCause()).getMessage()
							.contains("AttacksTest.csv"));
			assertTrue(
					"Load Attacks Method in the Game class should include the File line that has the error in the message of the Exception thrown, when a field is missing in the file ",
					((MissingFieldException) e.getCause()).getMessage()
							.contains("2"));

			assertTrue(
					"Load Attacks Method in the Game class should include the number of the missing fields in the message of the Exception thrown, when a field(s) is/are missing in the file ",
					((MissingFieldException) e.getCause()).getMessage()
							.contains("1"));
		}

		try {
			Game g = new Game();
			g.getAttacks().clear();
			executeLoadAttacks_CorrectFile_MissingField(g, Game.class);
		} catch (InvocationTargetException e) {
			fail("Load Attacks Method should work correctly if the file is correct; No Exception should be thrown");
		}

	}

	@Test(timeout = 1000)
	public void testLoadFoesMethod_Throws_MissingFieldException()
			throws Exception {
		try {
			Game g = new Game();
			g.getStrongFoes().clear();
			g.getWeakFoes().clear();
			executeLoadFoes_InCorrectFile_MissingField(g, Game.class);
		} catch (Exception e) {
			assertTrue(
					"Load Foes Method in the Game class should throw the appropriate Exception when a field is missing in the file",
					e.getCause() instanceof MissingFieldException);
			assertTrue(
					"Load Foes Method in the Game class should include the File name in the message of the Exception thrown when a field is missing in the file ",
					((MissingFieldException) e.getCause()).getMessage()
							.contains("FoesTest.csv"));
			assertTrue(
					"Load Foes Method in the Game class should include the File line that has the error in the message of the Exception thrown, when a field is missing in the file ",
					((MissingFieldException) e.getCause()).getMessage()
							.contains("4"));
			assertTrue(
					"Load Foes Method in the Game class should include the number of the missing fields in the message of the Exception thrown, when a field(s) is/are missing in the file  ",
					((MissingFieldException) e.getCause()).getMessage()
							.contains("1"));
		}

		try {
			Game g = new Game();
			g.getAttacks().clear();
			executeLoadFoes_CorrectFile_MissingField(g, Game.class);
		} catch (InvocationTargetException e) {
			fail("Load Foes Method should work correctly if the file is correct; No Exception should be thrown");
		}

	}

	@Test(timeout = 1000)
	public void testGameConstructor_Handles_MissingFieldException_OnLoadDragons()
			throws Exception {
		thrown = 0;
		Game g = null;
		try {
			g = new Game() {

				public void loadDragons(String f) throws MissingFieldException {
					thrown++;
					if (thrown++ == 1) {
						throw new MissingFieldException(f, 0, 2);
					}
					if (thrown == 4) {
						try {
							super.loadDragons(f);
						} catch (Exception e) {

						}
					}
				}
			};

		} catch (Exception e) {
			fail("Game constructor should handle Exceptions thrown on Loading Dragons");
		}

		ArrayList<Dragon> testDragons = g.getDragons();

		assertEquals(
				"Load Dragons should load from the auxiliary csv file when an exception is thrown",
				1, testDragons.size());

		assertEquals(
				"Dragon name is not loaded correctly from the auxiliary CSV file of Dragons",
				"Shenron", testDragons.get(0).getName());

		assertEquals(
				"Dragon senzuBeans is not loaded correctly from the auxiliary CSV file of Dragons",
				10, testDragons.get(0).getSenzuBeans());

		assertEquals(
				"Dragon ability points is not loaded correctly from the auxiliary CSV file of Dragons",
				5, testDragons.get(0).getAbilityPoints());

		assertEquals(
				"Super Attacks of Dragon doesn't contain the same number of super attacks given in the auxiliary CSV file",
				2, testDragons.get(0).getSuperAttacks().size());
		assertEquals(
				"Super Attack of Dragon is not loaded correctly from the auxiliary CSV file of Dragons",
				"Big Bang Kamehameha", testDragons.get(0).getSuperAttacks()
						.get(0).getName());
		assertEquals(
				"Super Attack of Dragon is not loaded correctly from the auxiliary CSV file of Dragons",
				"Emperor's Death Beam", testDragons.get(0).getSuperAttacks()
						.get(1).getName());

		assertEquals(
				"Ultimate Attacks of Dragon doesn't contain the same number of ultimate attacks given in the auxiliary CSV file",
				3, testDragons.get(0).getUltimateAttacks().size());
		assertEquals(
				"Ultimate Attack of Dragon is not loaded correctly from the auxiliary CSV file of Dragons",
				"Super Big Bang Kamehameha", testDragons.get(0)
						.getUltimateAttacks().get(0).getName());
		assertEquals(
				"Ultimate Attack of Dragon is not loaded correctly from the auxiliary CSV file of Dragons",
				"Final Shine Attack", testDragons.get(0).getUltimateAttacks()
						.get(1).getName());
		assertEquals(
				"Ultimate Attack of Dragon is not loaded correctly from the auxiliary CSV file of Dragons",
				"Final Galick Gun", testDragons.get(0).getUltimateAttacks()
						.get(2).getName());
	}

	@Test(timeout = 1000)
	public void testGameConstructor_Handles_MissingFieldException_OnLoadAttacks()
			throws Exception {
		thrown = 0;
		Game g = null;
		try {
			g = new Game() {
				public void loadAttacks(String f) throws MissingFieldException,
						UnknownAttackTypeException {
					thrown++;
					if (thrown++ == 1) {
						throw new MissingFieldException(f, 0, 2);
					}
					if (thrown == 4) {
						try {
							super.loadAttacks(f);
						} catch (Exception e) {
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
	public void testThrowingNotEnoughKi_MaximumCharge() throws Exception {
		Saiyan me = new Saiyan("saiyan");
		ArrayList<SuperAttack> superAttacks = new ArrayList<SuperAttack>();
		superAttacks.add(new MaximumCharge());
		superAttacks.add(new SuperAttack("Kamehameha", 250));
		me.setSuperAttacks(superAttacks);

		ArrayList<UltimateAttack> ultimateAttacks = new ArrayList<UltimateAttack>();
		ultimateAttacks.add(new SuperSaiyan());
		ultimateAttacks.add(new UltimateAttack("Spirit Bomb", 500));
		me.setUltimateAttacks(ultimateAttacks);

		NonPlayableFighter foe = new NonPlayableFighter("fighter", 1, 1, 10,
				1250, 50, 40, false, null, null);
		ArrayList<SuperAttack> superAttacks1 = new ArrayList<SuperAttack>();
		superAttacks1.add(new SuperAttack("Kamehameha", 250));
		foe.setSuperAttacks(superAttacks1);

		ArrayList<UltimateAttack> ultimateAttacks1 = new ArrayList<UltimateAttack>();
		ultimateAttacks1.add(new UltimateAttack("Super Kamehameha", 450));
		ultimateAttacks1.add(new SuperSaiyan());
		foe.setUltimateAttacks(ultimateAttacks1);

		Battle b = new Battle(me, foe);

		boolean thrown = false;
		me.setKi(0);
		((Fighter) b.getAttacker()).setKi(0);
		try {
			b.attack(me.getSuperAttacks().get(0));
		} catch (NotEnoughKiException e) {
			thrown = true;
		}
		assertFalse(
				"NotEnoughKiException should not be thrown if the user tries to use a Maximum charge attack",
				thrown);
	}

	@Test(timeout = 1000)
	public void testThrowingNotEnoughKi_PhysicalAttack() throws Exception {
		Saiyan me = new Saiyan("saiyan");
		ArrayList<SuperAttack> superAttacks = new ArrayList<SuperAttack>();
		superAttacks.add(new MaximumCharge());
		superAttacks.add(new SuperAttack("Kamehameha", 250));
		me.setSuperAttacks(superAttacks);

		ArrayList<UltimateAttack> ultimateAttacks = new ArrayList<UltimateAttack>();
		ultimateAttacks.add(new SuperSaiyan());
		ultimateAttacks.add(new UltimateAttack("Spirit Bomb", 500));
		me.setUltimateAttacks(ultimateAttacks);

		NonPlayableFighter foe = new NonPlayableFighter("fighter", 1, 1, 10,
				1250, 50, 40, false, null, null);
		ArrayList<SuperAttack> superAttacks1 = new ArrayList<SuperAttack>();
		superAttacks1.add(new SuperAttack("Kamehameha", 250));
		foe.setSuperAttacks(superAttacks1);

		ArrayList<UltimateAttack> ultimateAttacks1 = new ArrayList<UltimateAttack>();
		ultimateAttacks1.add(new UltimateAttack("Super Kamehameha", 450));
		ultimateAttacks1.add(new SuperSaiyan());
		foe.setUltimateAttacks(ultimateAttacks1);

		Battle b = new Battle(me, foe);

		boolean thrown = false;
		me.setKi(3);
		try {
			b.attack(new PhysicalAttack());
		} catch (NotEnoughKiException e) {
			thrown = true;
		}
		assertFalse(
				"NotEnoughKiException should not be thrown if the user tries to use a physical attack",
				thrown);
	}

	@Test(timeout = 1000)
	public void testThrowingNotEnoughKi_SuperAttack() throws Exception {
		Saiyan me = new Saiyan("saiyan");
		ArrayList<SuperAttack> superAttacks = new ArrayList<SuperAttack>();
		superAttacks.add(new MaximumCharge());
		superAttacks.add(new SuperAttack("Kamehameha", 250));
		me.setSuperAttacks(superAttacks);

		ArrayList<UltimateAttack> ultimateAttacks = new ArrayList<UltimateAttack>();
		ultimateAttacks.add(new SuperSaiyan());
		ultimateAttacks.add(new UltimateAttack("Spirit Bomb", 500));
		me.setUltimateAttacks(ultimateAttacks);

		NonPlayableFighter foe = new NonPlayableFighter("fighter", 1, 1, 10,
				1250, 50, 40, false, null, null);
		ArrayList<SuperAttack> superAttacks1 = new ArrayList<SuperAttack>();
		superAttacks1.add(new SuperAttack("Kamehameha", 250));
		foe.setSuperAttacks(superAttacks1);

		ArrayList<UltimateAttack> ultimateAttacks1 = new ArrayList<UltimateAttack>();
		ultimateAttacks1.add(new UltimateAttack("Super Kamehameha", 450));
		ultimateAttacks1.add(new SuperSaiyan());
		foe.setUltimateAttacks(ultimateAttacks1);

		Battle b = new Battle(me, foe);

		boolean thrown = false;
		me.setKi(1);
		try {
			b.attack(me.getSuperAttacks().get(1));
		} catch (NotEnoughKiException e) {
			thrown = true;
		}
		assertFalse(
				"NotEnoughKiException should not be thrown if the user tries to use a super attack attack while there is enough Ki",
				thrown);

		if (b.getAttacker() != foe)
			b.switchTurn();
		thrown = false;
		foe.setKi(0);
		try {
			b.attack(foe.getSuperAttacks().get(0));
		} catch (NotEnoughKiException e) {
			thrown = true;
			if (e.getMessage() == null)
				fail("NotEnoughKiException should be initialized with a descriptive customized message");
			assertEquals(
					"NotEnoughKiException should be initialized with the correct availableKi value of the attacker before it is thrown",
					0, e.getAvailableKi());
			assertEquals(
					"NotEnoughKiException should be initialized with the correct requiredKi value of the attack before it is thrown",
					1, e.getRequiredKi());
			assertTrue(
					"The thrown message of NotEnoughKiException should contain the requiredKi value",
					e.getMessage().contains(e.getRequiredKi() + ""));
			assertTrue(
					"The thrown message of NotEnoughKiException should contain the availableKi value",
					e.getMessage().contains(e.getAvailableKi() + ""));
		}
		assertTrue(
				"NotEnoughKiException should be thrown if the user tries to use a super attack while there is no enough Ki",
				thrown);
	}

	@Test(timeout = 1000)
	public void testThrowingNotEnoughKi_UltimateAttack() throws Exception {
		Saiyan me = new Saiyan("saiyan");
		ArrayList<SuperAttack> superAttacks = new ArrayList<SuperAttack>();
		superAttacks.add(new MaximumCharge());
		superAttacks.add(new SuperAttack("Kamehameha", 250));
		me.setSuperAttacks(superAttacks);

		ArrayList<UltimateAttack> ultimateAttacks = new ArrayList<UltimateAttack>();
		ultimateAttacks.add(new SuperSaiyan());
		ultimateAttacks.add(new UltimateAttack("Spirit Bomb", 500));
		me.setUltimateAttacks(ultimateAttacks);

		NonPlayableFighter foe = new NonPlayableFighter("fighter", 1, 1, 10,
				1250, 50, 40, false, null, null);
		ArrayList<SuperAttack> superAttacks1 = new ArrayList<SuperAttack>();
		superAttacks1.add(new SuperAttack("Kamehameha", 250));
		foe.setSuperAttacks(superAttacks1);

		ArrayList<UltimateAttack> ultimateAttacks1 = new ArrayList<UltimateAttack>();
		ultimateAttacks1.add(new UltimateAttack("Super Kamehameha", 450));
		ultimateAttacks1.add(new SuperSaiyan());
		foe.setUltimateAttacks(ultimateAttacks1);

		Battle b = new Battle(me, foe);

		boolean thrown = false;
		me.setKi(3);
		try {
			b.attack(me.getUltimateAttacks().get(0));
		} catch (NotEnoughKiException e) {
			thrown = true;
		}
		assertFalse(
				"NotEnoughKiException should not be thrown if the user tries to use an ultimate attack while there is enough Ki",
				thrown);

		if (b.getAttacker() != foe)
			b.switchTurn();
		thrown = false;
		foe.setKi(2);
		try {
			b.attack(foe.getUltimateAttacks().get(0));
		} catch (NotEnoughKiException e) {
			thrown = true;

			if (e.getMessage() == null)
				fail("NotEnoughKiException should be initialized with a descriptive customized message");
			assertEquals(
					"NotEnoughKiException should be initialized with the correct availableKi value of the attacker before it is thrown",
					2, e.getAvailableKi());
			assertEquals(
					"NotEnoughKiException should be initialized with the correct requiredKi value of the attack before it is thrown",
					3, e.getRequiredKi());
			assertTrue(
					"The thrown message of NotEnoughKiException should contain the requiredKi value",
					e.getMessage().contains(e.getRequiredKi() + ""));
			assertTrue(
					"The thrown message of NotEnoughKiException should contain the availableKi value",
					e.getMessage().contains(e.getAvailableKi() + ""));
		}
		assertTrue(
				"NotEnoughKiException should be thrown if the user tries to use an ultimate attack while there is no enough Ki",
				thrown);
	}

	@Test(timeout = 1000)
	public void testThrowingNotEnoughKi_SuperSaiyan() throws Exception {
		Saiyan me = new Saiyan("saiyan");
		ArrayList<SuperAttack> superAttacks = new ArrayList<SuperAttack>();
		superAttacks.add(new MaximumCharge());
		superAttacks.add(new SuperAttack("Kamehameha", 250));
		me.setSuperAttacks(superAttacks);

		ArrayList<UltimateAttack> ultimateAttacks = new ArrayList<UltimateAttack>();
		ultimateAttacks.add(new SuperSaiyan());
		ultimateAttacks.add(new UltimateAttack("Spirit Bomb", 500));
		me.setUltimateAttacks(ultimateAttacks);

		NonPlayableFighter foe = new NonPlayableFighter("fighter", 1, 1, 10,
				1250, 50, 40, false, null, null);
		ArrayList<SuperAttack> superAttacks1 = new ArrayList<SuperAttack>();
		superAttacks1.add(new SuperAttack("Kamehameha", 250));
		foe.setSuperAttacks(superAttacks1);

		ArrayList<UltimateAttack> ultimateAttacks1 = new ArrayList<UltimateAttack>();
		ultimateAttacks1.add(new UltimateAttack("Super Kamehameha", 450));
		ultimateAttacks1.add(new SuperSaiyan());
		foe.setUltimateAttacks(ultimateAttacks1);

		Battle b = new Battle(me, foe);

		if (b.getAttacker() != me)
			b.switchTurn();
		boolean thrown = false;
		me.setKi(3);
		try {
			b.attack(me.getUltimateAttacks().get(0));
		} catch (NotEnoughKiException e) {
			thrown = true;
		}
		assertFalse(
				"NotEnoughKiException should not be thrown if the user tries to use a Super Saiyan attack while there is enough Ki",
				thrown);

		if (b.getAttacker() != me)
			b.switchTurn();

		thrown = false;

		me.setKi(2);
		try {

			b.attack(me.getUltimateAttacks().get(0));
		} catch (NotEnoughKiException e) {
			thrown = true;

			if (e.getMessage() == null)
				fail("NotEnoughKiException should be initialized with a descriptive customized message");
			assertEquals(
					"NotEnoughKiException should be initialized with the correct availableKi value of the attacker before it is thrown",
					2, e.getAvailableKi());
			assertEquals(
					"NotEnoughKiException should be initialized with the correct requiredKi value of the attack before it is thrown",
					3, e.getRequiredKi());
			assertTrue(
					"The thrown message of NotEnoughKiException should contain the requiredKi value",
					e.getMessage().contains(e.getRequiredKi() + ""));
			assertTrue(
					"The thrown message of NotEnoughKiException should contain the availableKi value",
					e.getMessage().contains(e.getAvailableKi() + ""));
		}
		assertTrue(
				"NotEnoughKiException should be thrown if the user tries to use a Super Saiyan attack while there is no enough Ki",
				thrown);

	}

	@Test(timeout = 1000)
	public void testThrowingNotASaiyanException() throws Exception {
		Player p = new Player("");
		p.createFighter('E', "earthling");
		p.createFighter('S', "saiyan");

		boolean thrown = false;
		try {
			p.assignAttack(p.getFighters().get(0), new SuperSaiyan(), null);
		} catch (NotASaiyanException e) {
			thrown = true;
			if (e.getMessage() == null)
				fail("NotASaiyanException should be initialized with a descriptive customized message");
		}
		assertTrue(
				"AssignAttack(UltimateAttack u) Method can assign attacks of type SuperSaiyan to Saiyan fighters Only, otherwise a NotASaiyanException is thrown",
				thrown);

		thrown = false;
		try {
			p.assignAttack(p.getFighters().get(1), new SuperSaiyan(), null);
		} catch (NotASaiyanException e) {
			thrown = true;
			if (e.getMessage() == null)
				fail("NotASaiyanException should be initialized with a descriptive customized message");
		}
		assertFalse(
				"AssignAttack(UltimateAttack u) Method can assign attacks of type SuperSaiyan to Saiyan fighters Only, otherwise a NotASaiyanExceptoin is thrown",
				thrown);
	}

	public static boolean containsMethodName(Method[] methods, String name) {
		for (Method method : methods) {
			if (method.getName().equals(name))
				return true;
		}
		return false;
	}

	public static void clearWorld(Cell[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new EmptyCell();
			}
		}
	}

	public static void executeLoadAttacks_InCorrectFile_UnkownAttack(Object o,
			Class c) throws UnknownAttackTypeException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, FileNotFoundException {
		Method method = c.getDeclaredMethod("loadAttacks",
				new Class[] { String.class });
		method.setAccessible(true);

		PrintWriter attacksWriter = new PrintWriter("AttacksTest.csv");
		attacksWriter.println("MC,Maximum Charge,0");
		attacksWriter.println("NS,Super Saiyan,0");
		attacksWriter.println("SA,Kamehameha,250");
		attacksWriter.println("UA,Vanishing Ball,500");
		attacksWriter.close();
		method.invoke(o, "AttacksTest.csv");

	}

	public static void executeLoadAttacks_CorrectFile_UnknownAttack(Object o,
			Class c) throws UnknownAttackTypeException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, FileNotFoundException {
		Method method = c.getDeclaredMethod("loadAttacks",
				new Class[] { String.class });
		method.setAccessible(true);
		PrintWriter attacksWriter = new PrintWriter("AttacksTest.csv");
		attacksWriter.println("MC,Maximum Charge,0");
		attacksWriter.println("SS,Super Saiyan,0");
		attacksWriter.println("SA,Kamehameha,250");
		attacksWriter.println("UA,Vanishing Ball,500");
		attacksWriter.close();
		method.invoke(o, "AttacksTest.csv");

	}

	public static void executeLoadAttacks_InCorrectFile_MissingField(Object o,
			Class c) throws UnknownAttackTypeException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, FileNotFoundException {
		Method method = c.getDeclaredMethod("loadAttacks",
				new Class[] { String.class });
		method.setAccessible(true);
		PrintWriter attacksWriter = new PrintWriter("AttacksTest.csv");
		attacksWriter.println("MC,Maximum Charge,0");
		attacksWriter.println("Super Saiyan,0");
		attacksWriter.println("SA,Kamehameha,250");
		attacksWriter.println("UA,Vanishing Ball,500");
		attacksWriter.close();
		method.invoke(o, "AttacksTest.csv");

	}

	public static void executeLoadAttacks_CorrectFile_MissingField(Object o,
			Class c) throws UnknownAttackTypeException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, FileNotFoundException {
		Method method = c.getDeclaredMethod("loadAttacks",
				new Class[] { String.class });
		method.setAccessible(true);
		PrintWriter attacksWriter = new PrintWriter("AttacksTest.csv");
		attacksWriter.println("MC,Maximum Charge,0");
		attacksWriter.println("SS,Super Saiyan,0");
		attacksWriter.println("SA,Kamehameha,250");
		attacksWriter.println("UA,Vanishing Ball,500");
		attacksWriter.close();
		method.invoke(o, "AttacksTest.csv");

	}

	public static void executeLoadFoes_InCorrectFile_MissingField(Object o,
			Class c) throws Exception {
		Method method = c.getDeclaredMethod("loadFoes",
				new Class[] { String.class });
		method.setAccessible(true);
		PrintWriter foesWriter = new PrintWriter("FoesTest.csv");
		foesWriter.println("Goku,10,3000,350,400,5,6,TRUE");
		foesWriter.println("Kamehameha");
		foesWriter.println("Super Kamehameha,Spirit Bomb");
		foesWriter.println("Gohan (Kid),6,200,150,7,5,TRUE");
		foesWriter.println("Masenko,Maximum Charge");
		foesWriter.println("Explosive Assault");
		foesWriter.println("Krillin,5,1400,150,200,4,5,FALSE");
		foesWriter.println("Ki Blast Thrust,Kamehameha");
		foesWriter.println("");
		foesWriter.println("Yamcha,5,1250,150,100,3,4,FALSE");
		foesWriter.println("");
		foesWriter.println("");
		foesWriter.println("Navel,2,1250,200,50,3,4,FALSE");
		foesWriter.println("Beam Rifle");
		foesWriter.println("");

		foesWriter.close();

		method.invoke(o, "FoesTest.csv");

	}

	public static void executeLoadFoes_CorrectFile_MissingField(Object o,
			Class c) throws Exception {
		Method method = c.getDeclaredMethod("loadFoes",
				new Class[] { String.class });
		method.setAccessible(true);
		PrintWriter foesWriter = new PrintWriter("FoesTest.csv");
		foesWriter.println("Goku,10,3000,350,400,5,6,TRUE");
		foesWriter.println("Kamehameha");
		foesWriter.println("Super Kamehameha,Spirit Bomb");
		foesWriter.println("Gohan (Kid),6,1500,200,150,7,5,TRUE");
		foesWriter.println("Masenko,Maximum Charge");
		foesWriter.println("Explosive Assault");
		foesWriter.println("Krillin,5,1400,150,200,4,5,FALSE");
		foesWriter.println("Ki Blast Thrust,Kamehameha");
		foesWriter.println("");
		foesWriter.println("Yamcha,5,1250,150,100,3,4,FALSE");
		foesWriter.println("");
		foesWriter.println("");
		foesWriter.println("Navel,2,1250,200,50,3,4,FALSE");
		foesWriter.println("Beam Rifle");
		foesWriter.println("");

		foesWriter.close();

		method.invoke(o, "FoesTest.csv");

	}

}
