public class SingletonAccount {
	private static volatile AccountRepository accounts = null;

	private SingletonAccount() {
	}

	public static synchronized AccountRepository getInstance() {
		if (accounts == null) {
			accounts = new AccountRepository();
		}

		return accounts;
	}
}