package controllers;

import models.Book;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

import views.html.books.*;

import javax.inject.Inject;

public class BooksController extends Controller {

    @Inject
    FormFactory formFactory;

    // for all books
    public Result index() {
        List<Book> books = Book.find.all();

        return ok(index.render(books));
    }

    // to create book
    public Result create() {
        Form<Book> bookForm = formFactory.form(Book.class);

        return ok(create.render(bookForm));
    }

    // to save book
    public Result save() {
        Form<Book> bookForm = formFactory.form(Book.class).bindFromRequest();
        Book book = bookForm.get();
        book.save();
        return redirect(routes.BooksController.index());
    }

    public Result edit(Integer id) {
        Book book = Book.find.byId(id);
        if (book == null) {
            return notFound("Book Not Found");
        }
        Form<Book> bookForm = formFactory.form(Book.class).fill(book);
        return ok(edit.render(bookForm));
    }

    public Result update() {
        Book book = formFactory.form(Book.class).bindFromRequest().get();
        Book oldBook = Book.find.byId(book.id);

        if (oldBook == null) {
            return notFound("Book Not Found");
        }

        oldBook.title = book.title;
        oldBook.price = book.price;
        oldBook.author = book.author;
        oldBook.update();

        return redirect(routes.BooksController.index());
    }

    public Result destroy(Integer id) {
        Book book = Book.find.byId(id);

        if (book == null) {
            return notFound("Book Not Found");
        }

        book.delete();

        return redirect(routes.BooksController.index());
    }

    // for book details
    public Result show(Integer id) {
        Book book = Book.find.byId(id);

        if (book == null) {
            return notFound("Book Not Found");
        }

        return ok(show.render(book));
    }


}
