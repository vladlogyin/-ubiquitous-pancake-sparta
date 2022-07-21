package future.legends.pancake.model;

import java.util.Queue;

public class TrainingHub extends TraineeCentre{
    TrainingHub(){
        capacity = 100;
    }

    @Override
    void enrollTrainees(Queue<Trainee> waitingList) {
        if(waitingList.isEmpty()) return;

        int amountToEnroll = getAvailableSpots();
        if(waitingList.size() < amountToEnroll) amountToEnroll = waitingList.size();

        super.enrollTrainees(waitingList, amountToEnroll);
    }

    @Override
    void monthPassed() {
        monthsAlive++;
    }

}
