import view.GuestPageView;
import view.HostSubPageView;
import view.MainView;

public class Application {
    public static void main(String[] args) {
        MainView.getInstance().run();
        //HostSubPageView.getInstance().run();
        //GuestPageView.getInstance().run();
    }
}
