var Book = Java.type('com.glarimy.nashorn.Book');
var prepare = function prepareBook() {
	var book = new Book(1234, "Java Nashorn");
	return book;
}