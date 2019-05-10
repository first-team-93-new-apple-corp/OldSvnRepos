package newTextBasedAdventure;

import java.util.ArrayList;

public class ScenarioTreeBuilder implements ScenarioTree{
	public Scenario buildTree() {
		Scenario end1 = new Scenario("dream0");
		Scenario end2 = new Scenario("dream1");
		Scenario end3 = new Scenario("dream2");
		Scenario end4 = new Scenario("dream3");
		Scenario end5 = new Scenario("You didn't have any dreams");
	
		ArrayList<Scenario> children = new ArrayList<Scenario>();
		children.add(end1);
		children.add(end2);
		children.add(end3);
		children.add(end4);
		children.add(end5);
		ArrayList<String> options = new ArrayList<String>();
		options.add("Dream 0");
		options.add("Dream 1");
		options.add("Dream 2");
		options.add("Dream 3");
		options.add("Dream 4");
		
		Scenario talkInClassBreakfast = new Scenario("You talk to your friend but your teacher hears you and gives you a detention. Good going you think to yourself, now you have to stay after school to sit in an empty room with your teacher and do nothing, but oh well, it's not like you had anything better planned anyways I guess. The bell rings and it's time for lunch, you eat your food and then go to the rest of your classes. School ends and you go to detention, just as boring as you expected. You look around for ways to entertain yourself but fail, so you slouch in your desk and twiddle your thumbs. Dention ends and you go home and eat dinner and do homework. After a long day you decide to go to bed, which dream would you like to have?", options, children);
		
		children = new ArrayList<Scenario>();
		children.add(end1);
		children.add(end2);
		children.add(end3);
		children.add(end4);
		children.add(end5);
		options = new ArrayList<String>();
		options.add("Dream 0");
		options.add("Dream 1");
		options.add("Dream 2");
		options.add("Dream 3");
		options.add("Dream 4");
		
		Scenario payAttentionBreakfast = new Scenario("You pay attention in class, you kind of regret it, but when your friend gets a detention for talking to someone else in class you are glad you didn't start talking, plus you learned a thing or two about WWI like... like... well it looks like you already forgot it. The bell rings and it's time for lunch, you eat your food and then go to the rest of your classes. School ends and you go to detention, just as boring as you expected. You look around for ways to entertain yourself but fail, so you slouch in your desk and twiddle your thumbs. Dention ends and you go home and eat dinner and do homework. After a long day you decide to go to bed, which dream would you like to have?", options, children);
		
		children = new ArrayList<Scenario>();
		children.add(end1);
		children.add(end2);
		children.add(end3);
		children.add(end4);
		children.add(end5);
		options = new ArrayList<String>();
		options.add("Dream 0");
		options.add("Dream 1");
		options.add("Dream 2");
		options.add("Dream 3");
		options.add("Dream 4");
		
		Scenario takeNap = new Scenario("After a long day you decide to go to bed, which dream would you like to have?", options, children);
		
		children = new ArrayList<Scenario>();
		children.add(end1);
		children.add(end2);
		children.add(end3);
		children.add(end4);
		children.add(end5);
		options = new ArrayList<String>();
		options.add("Dream 0");
		options.add("Dream 1");
		options.add("Dream 2");
		options.add("Dream 3");
		options.add("Dream 4");
		
		Scenario eatDinner = new Scenario("Your mom calls you in for dinner and asks if you want to eat it or go to bed, you tell her you think you will be fine to eat it, and so you do. However, you throw up again and wonder how you could be this stupid to try and eat again. After a long day you decide to go to bed, which dream would you like to have?", options, children);
		
		children = new ArrayList<Scenario>();
		children.add(end1);
		children.add(end2);
		children.add(end3);
		children.add(end4);
		children.add(end5);
		options = new ArrayList<String>();
		options.add("Dream 0");
		options.add("Dream 1");
		options.add("Dream 2");
		options.add("Dream 3");
		options.add("Dream 4");

		Scenario skipDinner = new Scenario("Your mom calls you in for dinner and asks if you want to eat it or go to bed, you tell her you want to go to bed, which dream would you like to have?", options, children);
		
		children = new ArrayList<Scenario>();
		children.add(eatDinner);
		children.add(skipDinner);
		options = new ArrayList<String>();
		options.add("Eat dinner");
		options.add("Don't eat dinner");
		
		Scenario watchMovie = new Scenario("You chose to watch a movie in order to relax. The movie was very good, it was about a crazy man who has many tiny men working for him to make chocolate. The crazy man invites children into his workplace only to harm them while the workers sing and dance about it. That movie was kind of weird now that you think about it. Now it's time for dinner, would you like to eat dinner or go straight to bed?", options, children);
		
		children = new ArrayList<Scenario>();
		children.add(takeNap);
		children.add(watchMovie);
		options = new ArrayList<String>();
		options.add("Take a nap");
		options.add("Watch a Movie");
		
		Scenario payAttentionNoBreakfast = new Scenario("You pay attention in class, you kind of regret it, but when your friend gets a detention for talking to someone else in class you are glad you didn't start talking, plus you learned a thing or two about WWI like... like... well it looks like you already forgot it. The bell rings and it's finally time for lunch, you are starving since you skipped breakfast so you grab all then food in sight, however, this causes you to throw up and go home sick. You get home, would you like to take a nap or watch a movie?", options, children);
		
		children = new ArrayList<Scenario>();
		children.add(takeNap);
		children.add(watchMovie);
		options = new ArrayList<String>();
		options.add("Take a nap");
		options.add("Watch a Movie");
		
		Scenario talkInClassNoBreakfast = new Scenario("You talk to your friend but your teacher hears you and gives you a detention. Good going you think to yourself, now you have to stay after school to sit in an empty room with your teacher and do nothing, but oh well, it's not like you had anything better planned anyways I guess. The bell rings and it's finally time for lunch, you are starving since you skipped breakfast so you grab all then food in sight, however, this causes you to throw up and go home sick. You get home, would you like to take a nap or watch a movie?", options, children);
		
		children = new ArrayList<Scenario>();
		children.add(talkInClassBreakfast);
		children.add(payAttentionBreakfast);
		options = new ArrayList<String>();
		options.add("Talk to your friend");
		options.add("Pay attention to the teacher");
		
		Scenario eatBreakfast = new Scenario("You eat breakfast and head off to school, you get to school just in time before the bell. Class starts, but it's really boring, do you talk to your friend or stay quiet and pay attention?", options, children);
	
		children = new ArrayList<Scenario>();
		children.add(talkInClassNoBreakfast);
		children.add(payAttentionNoBreakfast);
		options = new ArrayList<String>();
		options.add("Talk to your friend");
		options.add("Pay attention to the teacher");
		
		Scenario skipBreakfast = new Scenario("You skip breakfast and head off to school, you step into class with enough time to chat with your friends, however, you feel a little hungry. Class begins but it's really boring, do you talk to your friend or stay quiet and pay attention?", options, children);
		
		children = new ArrayList<Scenario>();
		children.add(eatBreakfast);
		children.add(skipBreakfast);
		options = new ArrayList<String>();
		options.add("Eat breakfast");
		options.add("Skip breakfast");
		
		Scenario beginning = new Scenario("Buzzzzzzz buzzzzzzz, the sound of your alarm wakes you up, it's time to get ready for school. You are running behind, would you like to eat breakfast?", options, children);
		
		return beginning;
	}
}