# BankTransfer
Банк(класс Bank), в котором хранятся счета клиентов(класс Account) с двумя полями - money и accNumber.
Клиенты банка могут совершать переводы и запрашивать баланс в многопточном режиме. Если сумма перевода >= 50000, то перевод отправляется на проверку(метод isFraud). При обнаружение мошеннических действий блокируются оба счета(нельзя совершать любые операции)
