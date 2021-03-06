package ru.razhapov.example;

import java.util.ArrayList;
import java.util.List;

public class Users{
    private String name;
    private String number;
    private static long userId = 0;
    private long id = 0;
    public static List<Users> usersList = new ArrayList<Users>();
    public List<Contacts> userPhoneBook = new ArrayList<Contacts>();

    // конструктор
    public Users(String name, String number) {
        try {
            if(!name.isEmpty() && !checkString(name)){
                if (!number.isEmpty() && Long.parseLong(number) > 0 && checkString(number)) {
                    this.id = ++userId; // у каждого обекта уникальное id
                    this.name = name;
                    this.number = number;
                    usersList.add(this);
                    System.out.println("Пользователь "+ name +" добавлен!");
                }else{ }
            }
            else if(name.isEmpty()){
                System.out.println("Пользователь не добавлен: Пустое значение");
            }
            else {
                System.out.println("Пользователь не добавлен: Некорректно введено имя: " + name);
            }
        } catch (Exception e){
            System.out.println("Пользователь не добавлен: Некорректно введен номер: " + number);
        }
        System.out.println("");
    }

    // проверяем строку на наличие цифр
    public static boolean checkString(String string) {
        try {
            Long.parseLong(string);
        } catch (Exception e) {
            return false;// false при строковом аргументе
        }
        return true;// true при числах
    }

    // добавление пользователя(не контакт)
    public static void addUser(String name, String number){
        new Users(name, number);
    }

    // редактируем пользователя
    public void editUser(String name, String number){
        try {
            if(!checkString(name) && checkString(number)){ // если с аргументами все хорошо(имя это не цифры, а номер телефона это не буквы)
                System.out.println("Редактируем пользователя " + this.getName());
                for (int i = 0; i < usersList.size(); i++) { // цикл по списку пользователей
                    if (!name.isEmpty() && !number.isEmpty()){ // если имя и номер не пустые
                        if (usersList.get(i).getName().equals(this.getName())) {
                            System.out.println("Редактируем имя и номер");
                            usersList.get(i).setName(name); // изменяем имя у пользователя
                            usersList.get(i).setNumber(number); // меняем его номер
                        }
                    }
                    else if(number.isEmpty()) { // если пустой только номер изменяем имя
                        System.out.println("Редактируем имя");
                        if (usersList.get(i).getName().equals(this.getName())) {
                            usersList.get(i).setName(name);
                        }
                    }
                    else if (name.isEmpty()){ // если пустое имя, изменяем только номер телефона
                        System.out.println("Редактируем номер");
                        if (usersList.get(i).getName().equals(this.getName())) {
                            usersList.get(i).setNumber(number);
                        }
                    }
                }
            }
            else { // если есть ошибка в имени то выводим соответсвующее сообщение
                System.out.println("Некорректно введено имя: " + name);
            }
        } catch (Exception e){ // выбрасывает эксипшн при неправильном вводе номера телефона
            System.out.println("Некорректно введен номер: " + number);
        }
        System.out.println("");
    }

    // показать всех пользователей(не контактов)
    public static void showAllUsers() {
        System.out.println("Список всех пользователей: ");
        for (Users user : Users.usersList) {
            System.out.println(user);
        }
        System.out.println("");
    }

    // удаление пользователя(не контакта)
    public static void removeUser(String name){
        int count = 0;
        for (int i = 0; i < usersList.size(); i++) {
            if(usersList.get(i).getName().equals(name)){
                System.out.println("Удаляем телефонную книгу пользователя");
                for(int j = usersList.get(i).userPhoneBook.size()-1; j >= 0;){
                    usersList.get(i).userPhoneBook.remove(j);
                    j--;
                    count++;
                }
                System.out.println("Удалено " + count+ " контактов. Удаляем пользователя");
                usersList.remove(i);
                System.out.println("Пользователь " + name + " удалён");
                break;
            }
        }
        System.out.println("");
    }

    // поиск пользователя(не контакта)
    public static void findUser(String name){
        List<Users> list = new ArrayList<Users>();
        int count = 0;
        System.out.println("Поиск пользователя: " + name);
        for (int i = 0; i < usersList.size(); i++) {
            if(usersList.get(i).getName().contains(name)){
                list.add(usersList.get(i));
                count++;
            }
            else{
            }
        }
        if(count > 0) {
            System.out.println("Результатов поиска: " + count + "\n" + list);
        }
        else{
            System.out.println("Нет такого пользователя: " + name);
        }
        for (int i = list.size()-1; i >= 0; i--) {
            list.remove(i);
        }
        System.out.println("");
    }

    // получение пользователя по id
    public static Users userGetId(int id){
        Users user = null;
        System.out.println("Получаем пользователя по id: "+ id);
        for (int i = 0; i < usersList.size(); i++) {
            if(usersList.get(i).getId() == id){
                user = usersList.get(i);
                break;
            }
            else{
            }
        }
        return user;
    }

    // добавление контакта в телефонную книгу пользователя
    public void addContactList(String name, String number){
        System.out.println("Добавляем контакт " + name + " в телефонную книгу пользователя " + this.getName() + "\n");
        userPhoneBook.add(new Contacts(name, number));
    }

    // получение контакта по id
    public Contacts contactsGetId(int id){
        Contacts contacts = null;
        System.out.println("Получаем контакт по id: "+ id +" у пользователя " + this.getName());
        for (int i = 0; i < userPhoneBook.size(); i++) {
            if(usersList.get(i).getId() == id){
                contacts = userPhoneBook.get(i);
                break;
            }
            else{
            }
        }
        return contacts;
    }

    //поиск контакта в телефонной книге пользователя номеру телефона
    public void findContact(String number){
        List<Contacts> list = new ArrayList<Contacts>();
        int count = 0;
        System.out.println("Поиск контакта с номером телефона: " + number + " в телефонной книге пользователя " + this.getName());
        for (int i = 0; i < this.userPhoneBook.size(); i++) {
            if(this.userPhoneBook.get(i).getNumber().equals(number)){
                list.add(userPhoneBook.get(i));
                count++;
            }
            else{
            }
        }
        if(count > 0) {
            System.out.println("Результатов поиска: " + count + "\n" + list);
        }
        else{
            System.out.println("Нет такого контакта с таким номером: " + number + " в телефонной книге пользователя " + this.getName());
        }
        for (int i = list.size()-1; i >= 0; i--) {
            list.remove(i);
        }
        count = 0;
        System.out.println("");
    }

    // удаление контакта в телефонной книге
    public void removeContact(String name){
        int count = 0;
        System.out.println("Удаление контакта " + name + " пользователя " + this.getName());
        if(!name.isEmpty() && !checkString(name)){
            for (int i = 0; i < this.userPhoneBook.size(); i++) {
                if(name.equals(this.userPhoneBook.get(i).getName())){
                    this.userPhoneBook.remove(i);
                    System.out.println("Контакт " + name +" удалён \n");
                    count++;
                    break;
                }
            }
            if(count == 0){
                System.out.println("Контакт "+ name +" не найден\n");
            }
        }
        else if(name.isEmpty()){
            System.out.println("Пустое значение\n");
        }
        else if (checkString(name)){
            System.out.println("Вы ввели цифры\n");
        }
    }

    // показать все контакты пользователя
    public void showAllContactUserPhoneBook(){
        System.out.println("Список всех контактов, пользователя " +this.getName()+ ": ");
        for(int i = 0; i < this.userPhoneBook.size()-1; i++){
            System.out.println(userPhoneBook.toString());
        }

        System.out.println("");
    }

    // редактирование контакта в телефонной книге пользователя
    public void editContactInUserPhoneBook(String oldname, String newname, String number){
        try {
            if(!checkString(newname) && checkString(number)){ // если с аргументами все хорошо(имя это не цифры, а номер телефона это не буквы)
                System.out.println("Редактируем контакт " + oldname + " пользователя " + this.getName());
                for (int i = 0; i < this.userPhoneBook.size(); i++) { // цикл по списку пользователей
                    if (!newname.isEmpty() && !number.isEmpty()){ // если имя и номер не пустые
                        if (userPhoneBook.get(i).getName().equals(oldname)) {
                            System.out.println("Редактируем имя и номер");
                            userPhoneBook.get(i).setName(newname); // изменяем имя у контакта
                            userPhoneBook.get(i).setNumber(number); // меняем его номер
                        }
                    }
                    else if(number.isEmpty()) { // если пустой только номер изменяем имя
                        System.out.println("Редактируем имя");
                        if (userPhoneBook.get(i).getName().equals(oldname)) {
                            userPhoneBook.get(i).setName(newname);
                        }
                    }
                    else if (newname.isEmpty()){ // если пустое имя, изменяем только номер телефона
                        System.out.println("Редактируем номер");
                        if (userPhoneBook.get(i).getName().equals(oldname)) {
                            userPhoneBook.get(i).setNumber(number);
                        }
                    }
                }
            }
            else if(!checkString(newname)){ // если если имя введено верно, но номер с ошибкой
                System.out.println("Некорректно введен номер: " + number);
            }
            else { // если есть ошибка в имени то выводим соответсвующее сообщение
                System.out.println("Некорректно введено имя: " + newname);
            }
        } catch (Exception e){ // выбрасывает эксипшн при неправильном вводе номера телефона
            System.out.println("Некорректно введен номер: " + number);
        }
        System.out.println("");
    }


    @Override
    public String toString() {
        return "Имя: " + this.name
                + " Номер: " + this.number
                + " id: " + this.id
                + " Телефонный справочник: " + userPhoneBook
                + "\n";

    }


    // setters and getters

    public long getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }
}
