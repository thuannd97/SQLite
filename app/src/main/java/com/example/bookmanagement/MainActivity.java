package com.example.bookmanagement;

import android.app.Dialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Book> books;
    private RecyclerView recyclerViewBook;
    private BookAdapter bookAdapter;
    private ImageButton btnAddBook;
    private EditText txtKeyWord;
    private ImageButton btnSearch;

    private View.OnClickListener mOnItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            final Book thisItem = books.get(position);

            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.dialog_edit_book);
            final EditText txtBookName = dialog.findViewById(R.id.txtBookName);
            final EditText txtAuthor = dialog.findViewById(R.id.txtAuthor);
            Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            Button btnDelete = dialog.findViewById(R.id.btnDelete);

            txtBookName.setText(thisItem.getBookName());
            txtAuthor.setText(thisItem.getAuthor());

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("".equals(txtBookName.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Phai nhap ten sach!", Toast.LENGTH_SHORT).show();
                    } else {
                        String newBookName = txtBookName.getText().toString().trim();
                        String newAuthor = txtAuthor.getText().toString().trim();
                        thisItem.setBookName(newBookName);
                        thisItem.setAuthor(newAuthor);
                        editBook(thisItem);
                        Toast.makeText(MainActivity.this, "cap nhat thanh cong!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        showBooks(getAllBook());
                    }
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteBook(thisItem.getId());
                    Toast.makeText(MainActivity.this, getAllBook().size() + "", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    showBooks(getAllBook());
                }
            });

            dialog.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddBook = findViewById(R.id.btnAddBook);
        txtKeyWord = findViewById(R.id.txtKeyword);
        btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Book> searchResults = searchBook(txtKeyWord.getText().toString().toLowerCase().trim());
                Log.d("searchResult size: ", searchResults.size()+"");
                showBooks(searchResults);
            }
        });

        createTable();

        recyclerViewBook = findViewById(R.id.rcvBook);
        showBooks(getAllBook());
        btnAddBook.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        dialogAddBook();
    }

    // tao bang
    public void createTable() {
        BaseApplication.bookDBHelper.QueryData("CREATE TABLE IF NOT EXISTS book(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "book_name VARCHAR(45), " +
                "author VARCHAR(45))");
    }

    // them du lieu
    public void addBook(Book book) {
        BaseApplication.bookDBHelper.QueryData("INSERT INTO book(`book_name`, `author`) VALUES('" + book.getBookName() +
                "', '" + book.getAuthor() +
                "')");
    }

    // lay du lieu
    public List<Book> getAllBook() {
        books = new ArrayList<Book>();
        Cursor booksFromSQLite = BaseApplication.bookDBHelper.GetData("SELECT * FROM book");
        while (booksFromSQLite.moveToNext()) {
            Book book = new Book();
            book.setId(booksFromSQLite.getInt(0));
            book.setBookName(booksFromSQLite.getString(1));
            book.setAuthor(booksFromSQLite.getString(2));
            books.add(book);
        }
        return books;
    }

    public void editBook(Book book) {
        BaseApplication.bookDBHelper.QueryData("UPDATE book SET book_name = '" + book.getBookName() +
                "', author = '" + book.getAuthor() +
                "' WHERE id=" + book.getId() +
                "");
    }

    public void deleteBook(int id) {
        BaseApplication.bookDBHelper.QueryData("DELETE FROM book where id='" + id +
                "'");
    }

    public List<Book> searchBook(String keyWord) {
        List<Book> books = new ArrayList<Book>();
        Cursor bookFromSQLite = BaseApplication.bookDBHelper.GetData("SELECT * FROM book WHERE book_name LIKE'" + keyWord +
                "%' OR author LIKE'" + keyWord +
                "%'");
        while (bookFromSQLite.moveToNext()) {
            Book book = new Book();
            book.setId(bookFromSQLite.getInt(0));
            book.setBookName(bookFromSQLite.getString(1));
            book.setAuthor(bookFromSQLite.getString(2));
            books.add(book);
        }
        return books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void showBooks(List<Book> books) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewBook.setLayoutManager(layoutManager);
        bookAdapter = new BookAdapter(books, this);
        bookAdapter.setmOnItemtClickListener(mOnItemClickListener);
        recyclerViewBook.setAdapter(bookAdapter);
    }

    public void dialogAddBook() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_book);
        final EditText txtBookName = dialog.findViewById(R.id.txtBookName);
        final EditText txtAuthor = dialog.findViewById(R.id.txtAuthor);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(txtBookName.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Phai nhap ten sach!", Toast.LENGTH_SHORT).show();
                } else {
                    Book book = new Book();
                    book.setBookName(txtBookName.getText().toString());
                    book.setAuthor(txtAuthor.getText().toString());
                    addBook(book);
                    Toast.makeText(MainActivity.this, "successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    showBooks(getAllBook());
                }
            }
        });

        dialog.show();
    }

}
