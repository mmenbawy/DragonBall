package dragonball.controller;
	
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.io.IOException;

	import javax.swing.ImageIcon;
	//import dragonball.view.WorldView;
	import javax.swing.JButton;
	import javax.swing.JFileChooser;
	import javax.swing.JOptionPane;
	import javax.swing.JRadioButtonMenuItem;
	import javax.swing.filechooser.FileNameExtensionFilter;

	import dragonball.model.attack.Attack;
	import dragonball.model.attack.SuperAttack;
	import dragonball.model.attack.UltimateAttack;
	import dragonball.model.battle.Battle;
	import dragonball.model.battle.BattleEvent;
	import dragonball.model.cell.Collectible;
	import dragonball.model.character.fighter.Fighter;
	import dragonball.model.character.fighter.NonPlayableFighter;
	import dragonball.model.character.fighter.PlayableFighter;
	import dragonball.model.dragon.Dragon;
	import dragonball.model.dragon.DragonWish;
	import dragonball.model.dragon.DragonWishType;
	import dragonball.model.exceptions.DuplicateAttackException;
	import dragonball.model.exceptions.MaximumAttacksLearnedException;
	import dragonball.model.exceptions.NotASaiyanException;
	import dragonball.model.exceptions.NotEnoughAbilityPointsException;
	import dragonball.model.exceptions.NotEnoughKiException;
	import dragonball.model.exceptions.NotEnoughSenzuBeansException;
	import dragonball.model.game.Game;
	import dragonball.model.game.GameListener;
	import dragonball.model.game.GameState;
	import dragonball.model.player.Player;
	import dragonball.view.BattleView;
	import dragonball.view.DragonView;
	//import dragonball.view.DragonView;
	import dragonball.view.GameView;
	import dragonball.view.TournamentView;
	import dragonball.view.WorldView;
	import javafx.scene.media.Media;
	import javafx.scene.media.MediaPlayer;

	public class TournamentControl  implements GameListener,ActionListener {
		private Game game;
		private GameView gameView;
		private BattleView battleView;
		private DragonView dragonView;
		private WorldView worldView;
		private TournamentView tournament;
		public TournamentControl() throws IOException{
			gameView = new GameView(this);
		}
		public void actionPerformed(ActionEvent e){
			if(game==null|(game!=null&&game.getState()!=GameState.DRAGON)){
				switch (e.getActionCommand()){
				case "New Game":String name="";
				while(name!=null&&name.equals(""))
					name = JOptionPane.showInputDialog(gameView,"Enter your name","New Game",JOptionPane.QUESTION_MESSAGE);
				if(name!=null)
					while(true){
						try {
							game = new Game();
							game.getPlayer().setName(name);
							String[] races = {"Saiyan","Namekian","Majin","Earthling","Frieza"};
							String race = (String)JOptionPane.showInputDialog(gameView, "Choose a race for your first Fighter", "New Fighter", JOptionPane.QUESTION_MESSAGE, null, races, "Majin");	
							if(race==null)
								break;
							char actualRace = race.charAt(0);
							name="";
							while(name!=null&&name.equals(""))
								name = JOptionPane.showInputDialog(gameView,"Choose a name for your first Fighter","New Fighter",JOptionPane.QUESTION_MESSAGE);
							if(name!=null)
								game.getPlayer().createFighter(actualRace, name);
							// Go to WolrdView
							game.setListener(this);
							worldView =new WorldView(this,game.getWorld(),game.getPlayer());
							gameView.setVisible(false);
							break;
						} catch(Exception e1){

						}
					}
				break;
				case "Load Game" : JFileChooser loader = new JFileChooser();
				loader.setFileFilter(new FileNameExtensionFilter("Java Serializable files","ser"));
				int returnVal;
				while(true){
					returnVal = loader.showOpenDialog(gameView);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						try {
							game = new Game();
							game.load(loader.getSelectedFile().getPath());
							game.setListener(this);
							worldView = new WorldView(this,game.getWorld(),game.getPlayer());
							worldView.update();
							gameView.setVisible(false);
							break;
						} catch (ClassNotFoundException e1) {
							// 	TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// 	TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1){
							JOptionPane.showMessageDialog(null, "Not a valid file!");
						}
					}
					else{
						break;
					}
				}
				break;
				case "Tournament":String[] numbers = new String[16];
				for(int i = 1;i<=16;i++)
					numbers[i-1] = ""+i;
				int num = Integer.parseInt((String)JOptionPane.showInputDialog(gameView, "Choose a race for your first Fighter", "New Fighter", JOptionPane.QUESTION_MESSAGE, null, numbers, "1"));
				Player p = new Player("test");
				for(int i = 0;i<num;i++){
					String[] races = {"Saiyan","Namekian","Majin","Earthling","Frieza"};
					String race = (String)JOptionPane.showInputDialog(gameView, "Choose a race for your first Fighter", "New Fighter", JOptionPane.QUESTION_MESSAGE, null, races, "Majin");	
					if(race==null)
						break;
					char actualRace = race.charAt(0);
					name="";
					while(name!=null&&name.equals(""))
						name = JOptionPane.showInputDialog(gameView,"Choose a name for your first Fighter","New Fighter",JOptionPane.QUESTION_MESSAGE);
					if(name!=null)
						p.createFighter(actualRace, name);
				}
				tournament = new TournamentView(this, p.getFighters());
				break;
				
				case "Attack":if(!(battleView.getSelectedAttack() instanceof String)){
					Attack attack = (Attack) battleView.getSelectedAttack();
					Battle battle  = battleView.getBattle();
					try {
						battle.attack(attack);
						if(((Fighter)battle.getFoe()).getHealthPoints()!=0){
							battle.play();
						}
					} catch (NotEnoughKiException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
				break;
				case "Block" : battleView.getBattle().block();
				battleView.getBattle().play();
				break;
				case "Use" : 
					try {
						battleView.getBattle().use(game.getPlayer(),Collectible.SENZU_BEAN);
						battleView.getBattle().play();
					} catch (NotEnoughSenzuBeansException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					break;
				case "Save": JFileChooser saver = new JFileChooser();
				int returnVal1 = saver.showSaveDialog(worldView);
				if(returnVal1==JFileChooser.APPROVE_OPTION){
					try {
						game.save(saver.getSelectedFile().getPath());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				break;
				case "Create Fighter":
					String[] races = {"Saiyan","Namekian","Majin","Earthling","Frieza"};
					String race = (String)JOptionPane.showInputDialog(worldView, "Choose a race for your first Fighter", "New Fighter", JOptionPane.QUESTION_MESSAGE, null, races, "Majin");	
					if(race==null)
						break;
					char actualRace = race.charAt(0);
					name="";
					while(name!=null&&name.equals(""))
						name = JOptionPane.showInputDialog(worldView,"Choose a name for your first Fighter","New Fighter",JOptionPane.QUESTION_MESSAGE);
					if (name!=null&&!name.equals(""))
					for(int i=0;i<game.getPlayer().getFighters().size();i++){
						if(name.equals(game.getPlayer().getFighters().get(i).getName())){
							worldView.display("sorry but you can't create a new fighter with the same name" );
							name = JOptionPane.showInputDialog(worldView,"Choose a name for your first Fighter","New Fighter",JOptionPane.QUESTION_MESSAGE);
							i--;
						}
					}
					if(name!=null){
						game.getPlayer().createFighter(actualRace, name);
						worldView.updateFighters();
					}
					break;
				case "Fighter Selected": String s = ((JRadioButtonMenuItem)e.getSource()).getText();
				for(int i=0;i<game.getPlayer().getFighters().size();i++){
					if (game.getPlayer().getFighters()!= null && s.equals(game.getPlayer().getFighters().get(i).getName()))
						game.getPlayer().setActiveFighter(game.getPlayer().getFighters().get(i));
					worldView.update();
				}
				break;
				//upgrading the player case
				case "Max Health Points":
					try {
						game.getPlayer().upgradeFighter(game.getPlayer().getActiveFighter(), 'H');
						worldView.update();
						worldView.display("Your new Max Health Points is "+game.getPlayer().getActiveFighter().getMaxHealthPoints(), new ImageIcon("health.gif"));

					} catch (NotEnoughAbilityPointsException e1) {
						worldView.display(e1.getMessage());
					}
					break;
				case "Max Ki":
					try {
						game.getPlayer().upgradeFighter(game.getPlayer().getActiveFighter(), 'K');
						worldView.update();
						worldView.display("Your new Max Ki is "+game.getPlayer().getActiveFighter().getMaxKi(),new ImageIcon("ki.gif") );


					} catch (NotEnoughAbilityPointsException e1) {
						worldView.display(e1.getMessage());
					}
					break;
				case "Max Stamina":	
					try {
						game.getPlayer().upgradeFighter(game.getPlayer().getActiveFighter(), 'S');
						worldView.update();
						worldView.display("Your new Max Stamina is "+game.getPlayer().getActiveFighter().getMaxStamina(), new ImageIcon("stamina.gif"));

					} catch (NotEnoughAbilityPointsException e1) {
						worldView.display(e1.getMessage());
					}
					break;
				case "Physical Damage":
					try {
						game.getPlayer().upgradeFighter(game.getPlayer().getActiveFighter(), 'P');
						worldView.update();
						worldView.display("Your new Physical Damage is  "+game.getPlayer().getActiveFighter().getPhysicalDamage(), new ImageIcon("physical.gif"));


					} catch (NotEnoughAbilityPointsException e1) {
						worldView.display(e1.getMessage());
					}
					break;
				case "Blast Damage":
					try {
						game.getPlayer().upgradeFighter(game.getPlayer().getActiveFighter(), 'B');
						worldView.update();
						worldView.display("Your new Blast Damage "+game.getPlayer().getActiveFighter().getBlastDamage(), new ImageIcon("blast.gif"));


					} catch (NotEnoughAbilityPointsException e1) {
						worldView.display(e1.getMessage());
					}
					break;
				case "Super Attack":
					int size = game.getPlayer().getSuperAttacks().size();
					SuperAttack[] superattacks  = new SuperAttack[size];
					for(int i =0;i<size;i++){
						superattacks[i] = game.getPlayer().getSuperAttacks().get(i);
					}
					SuperAttack superattack = (SuperAttack)JOptionPane.showInputDialog(worldView, "Choose an attack to be assigned to "+game.getPlayer().getActiveFighter().getName(), "Assign Attack", JOptionPane.QUESTION_MESSAGE, null, superattacks,null);	
					if(superattack!=null){
						try {
							game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(), superattack, null);
						} catch (MaximumAttacksLearnedException e1) {
							size = game.getPlayer().getActiveFighter().getSuperAttacks().size();
							SuperAttack[] superattacksf  = new SuperAttack[size];
							for(int i =0;i<size;i++){
								superattacksf[i] = game.getPlayer().getActiveFighter().getSuperAttacks().get(i);
							}
							SuperAttack superattackf = (SuperAttack)JOptionPane.showInputDialog(worldView, e1.getMessage()+"\nChoose an attack to be replaced", "Assign Attack", JOptionPane.QUESTION_MESSAGE, null, superattacksf,null);
							if(superattackf!=null)
								try {
									game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(), superattack, superattackf);
								} catch (MaximumAttacksLearnedException e2) {

								} catch (DuplicateAttackException e2) {

								} catch (NotASaiyanException e2) {
									worldView.display(e1.getMessage());
								}
						} catch (DuplicateAttackException e1) {
							worldView.display(e1.getMessage());
						} catch (NotASaiyanException e1) {
							worldView.display(e1.getMessage());
						}
						
					}
					worldView.updateinfo();
					break;
				case "Ultimate Attack":
					int size1 = game.getPlayer().getUltimateAttacks().size();
					UltimateAttack[] ultimateattacks  = new UltimateAttack[size1];
					for(int i =0;i<size1;i++){
						ultimateattacks[i] = game.getPlayer().getUltimateAttacks().get(i);
					}
					UltimateAttack ultimateattack = (UltimateAttack)JOptionPane.showInputDialog(worldView, "Choose an attack to be assigned to "+game.getPlayer().getActiveFighter().getName(), "Assign Attack", JOptionPane.QUESTION_MESSAGE, null, ultimateattacks,null);	
					if(ultimateattack!=null){
						try {
							game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(), ultimateattack, null);
						} catch (MaximumAttacksLearnedException e1) {
							size = game.getPlayer().getActiveFighter().getUltimateAttacks().size();
							UltimateAttack[] ultimateattacksf  = new UltimateAttack[size];
							for(int i =0;i<size;i++){
								ultimateattacksf[i] = game.getPlayer().getActiveFighter().getUltimateAttacks().get(i);
							}
							UltimateAttack ultimateattackf = (UltimateAttack)JOptionPane.showInputDialog(worldView, e1.getMessage()+"\nChoose an attack to be replaced", "Assign Attack", JOptionPane.QUESTION_MESSAGE, null, ultimateattacksf,null);
							if(ultimateattackf!=null)
								try {
									game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(), ultimateattack, ultimateattackf);
								} catch (MaximumAttacksLearnedException e2) {

								} catch (DuplicateAttackException e2) {

								} catch (NotASaiyanException e2) {
									worldView.display(e1.getMessage());
								}
						} catch (DuplicateAttackException e1) {
							worldView.display(e1.getMessage());
						} catch (NotASaiyanException e1) {
							worldView.display(e1.getMessage());
						}
					}
					worldView.updateinfo();

					break;
				case "Load Last Saved":
					break;
				case "Exit!":
					Object[] options = {"Yes, please", "No, thanks","Cancel"};
					int n = JOptionPane.showOptionDialog(worldView,
							"Would you like to save the game before exiting ?",
									"Exit",
									JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE
									,null,options,options[2]);
					if (n==0){
						JFileChooser saver1 = new JFileChooser();
						int returnVal11 = saver1.showSaveDialog(worldView);
						if(returnVal11==JFileChooser.APPROVE_OPTION){
							try {
								game.save(saver1.getSelectedFile().getPath());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						
						
					}
						System.exit(0);

					
					}
					else if (n==1)
					System.exit(0);
					break;
			}
			}

			else
				onDragonView(e);
		}
		private void onDragonView(ActionEvent e) {
			JButton btn=(JButton)e.getSource();
			String text=btn.getText();

			DragonWishType type=null;
			DragonWish dragonWish=null;
			Dragon dragon=dragonView.getDragon();
			int random1= (int)((dragon.getSuperAttacks()).size()*Math.random());
			SuperAttack superAttack=(dragon.getSuperAttacks()).get(random1);
			int random2= (int)((dragon.getUltimateAttacks()).size()*Math.random());
			UltimateAttack ultimateAttack=(dragon.getUltimateAttacks()).get(random2);

			String message="Your Wish Has Been Granted."+'\n'+"You have gained: ";
			String note="On Your Wish";

			switch(text){
			case "Super Attack": 
				type=DragonWishType.SUPER_ATTACK ;
				dragonWish=new DragonWish(dragon, type, superAttack);
				JOptionPane.showMessageDialog(dragonView,message+ superAttack.getName()+" attack.",note, JOptionPane.INFORMATION_MESSAGE,new ImageIcon("superAttackm.png"));break;

			case "Ultimate Attack": 
				type=DragonWishType.ULTIMATE_ATTACK;
				dragonWish=new DragonWish(dragon, type, ultimateAttack);
				JOptionPane.showMessageDialog(dragonView,message+ ultimateAttack.getName()+" attack.",note, JOptionPane.INFORMATION_MESSAGE,new ImageIcon("sa.jpg"));break;

			case "Senzu Beans": 
				type=DragonWishType.SENZU_BEANS;
				dragonWish=new DragonWish(dragon, type, dragon.getSenzuBeans()); 
				JOptionPane.showMessageDialog(dragonView,message+dragon.getSenzuBeans()+" Senzu Beans.",note, JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Senzum4.jpg"));break;

			case "Ability Points": 
				type=DragonWishType.ABILITY_POINTS ;
				dragonWish=new DragonWish(dragon, type, dragon.getAbilityPoints());
				JOptionPane.showMessageDialog(dragonView,message+dragon.getAbilityPoints()+" ability points.",note, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("abilityPointsm.png"));break;
			}

			game.onWishChosen(dragonWish);
			dragonView.setVisible(false);
			worldView.update();
			worldView.setVisible(true);

		}
		public void onBattleEvent(BattleEvent e) throws IOException{
			switch (e.getType()){
			case ATTACK :
				battleView.attackUsed(e.getAttack());battleView.updateScreen();
				break;
			case STARTED :
				worldView.display("You have encountered a foe");
				battleView=new BattleView((Battle)e.getSource(),this);
				worldView.setVisible(false);
				break;
			case BLOCK:
				battleView.blocked();battleView.updateScreen();
				break;
			case USE : battleView.used();battleView.updateScreen();
			break;
			case ENDED : if(e.getWinner().equals(((Battle)e.getSource()).getFoe())){
				JOptionPane.showMessageDialog(battleView, ((Fighter)e.getWinner()).getName()+" has won the battle!");
				battleView.setVisible(false);
				worldView = new WorldView(this,game.getWorld(),game.getPlayer());

			}
			else{
				PlayableFighter me = ((PlayableFighter)e.getWinner());
				String tmp = me.getName()+" has won the battle!\n";
				tmp = ((NonPlayableFighter)((Battle)e.getSource()).getFoe()).isStrong()?(tmp+"You defeated a boss and entered a new map!\n"):tmp;
				tmp += me.getName()+"'s XP is now "+me.getXp()+'\n';
				tmp =(game.getNewAttacks().size()!=0)?(tmp+"You acquired the following attacks:\n"):tmp;
				for(int i = 0;i<game.getNewAttacks().size();i++){
					tmp+=game.getNewAttacks().get(i).getName()+", ";
				}
				tmp+='\n';
				if(game.getNewLevel()-game.getLastLevel()>0){
					tmp += "You are now level "+me.getLevel()+"!\n";
					tmp += "Target XP: "+me.getTargetXp()+"\n";
					tmp += me.getName()+" gained "+(me.getAbilityPoints()-game.getOldAbilityPoints())+" ability points"+"\n";
				}
				JOptionPane.showMessageDialog(battleView, tmp);
				if(((NonPlayableFighter)((Battle)e.getSource()).getFoe()).isStrong())
					worldView = new WorldView(this,game.getWorld(),game.getPlayer());
				else{
					worldView.setVisible(true);
					worldView.update();
				}
				battleView.setVisible(false);
			}
			}
		}
		public static void main(String[] args) throws IOException{
			new GUI();
//	        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//	            public void run() {
//	            	int returnVal = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//	                if(returnVal==JOptionPane.YES_OPTION)
//	                	System.exit(1);
//	            }
//	        }));
			//playSound("DBSound.mp3");
		}
		@Override
		public void onDragonCalled(Dragon dragon) {
			worldView.setVisible(false);
			dragonView = new DragonView(dragon, this);
		}
		@Override
		public void onCollectibleFound(Collectible collectible) {
			worldView.display("You have found a "+collectible.toString());
			worldView.update();
		}
		public Game getGame() {
			// TODO Auto-generated method stub
			return game;
		}
		/*@Override
		public void menuSelected(MenuEvent e) {
			String s=	worldView.getswitchfselected();
			for(int i=0;i<game.getPlayer().getFighters().size();i++){
	            if (game.getPlayer().getFighters()!= null && s.equals(game.getPlayer().getFighters().get(i).getName()))
	            	game.getPlayer().setActiveFighter(game.getPlayer().getFighters().get(i));
	            worldView.update();

			}

		}
		@Override
		public void menuDeselected(MenuEvent e) {
			String s=	worldView.getswitchfselected();

			for(int i=0;i<game.getPlayer().getFighters().size();i++){
	            if (game.getPlayer().getFighters()!= null && s.equals(game.getPlayer().getFighters().get(i).getName()))
	            	game.getPlayer().setActiveFighter(game.getPlayer().getFighters().get(i));
	            worldView.update();

			}



		}
		@Override
		public void menuCanceled(MenuEvent e) {


		}*/
		public void loadLast(String lastSavedFile) throws ClassNotFoundException, IOException {
			game.load(lastSavedFile);
			game.setListener(this);
		}
		public static void playSound(String url) {
			Media hit = new Media(url);
			MediaPlayer mediaPlayer = new MediaPlayer(hit);
			mediaPlayer.play();
		}
	}


