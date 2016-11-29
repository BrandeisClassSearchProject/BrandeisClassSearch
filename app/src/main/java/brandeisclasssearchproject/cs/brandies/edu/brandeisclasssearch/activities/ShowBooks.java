package brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.BaseAdapter;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import brandeisclasssearchproject.cs.brandies.edu.brandeisclasssearch.R;

public class ShowBooks extends AppCompatActivity {

    private ArrayList<Bitmap> bitmapList;
    private Bitmap bitmap;
    private ListView listView;
    private ArrayList<Book> bookList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_books);

        Intent i =getIntent();
        ArrayList<String> list =i.getExtras().getStringArrayList("list");

        if (list.size()>1) {
            for (int x = 0; x<list.size(); x+=2) {
                Log.i("showBooks",list.get(x)+" " +list.get(x+1));
                Book book = new Book(list.get(x), list.get(x+1));
                bookList.add(book);
            }
        } else {
            return;
        }

        listView = (ListView) findViewById(R.id.BookList);

        operationBI task = new operationBI();
        task.execute();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.right_out,R.anim.left_in);

    }

    private class BooksAdapter extends BaseAdapter {

        protected ArrayList <Book> list = new ArrayList<>();

        private BooksAdapter (ArrayList<Book> bookList) {
            list = bookList;
            Log.i("BooksAdapter",String.valueOf(bookList.size()));
        }

        public int getCount() {
                return list.size();
            }

        public Object getItem (int index) {
                return list.get(index);
            }

        public long getItemId (int index) {
                return index;
            }

        private class ViewHolder {
            ImageView image;
            TextView text;
        }

        public View getView (int index, View view, ViewGroup parent) {
            Log.i("getView",String.valueOf(index));
            LayoutInflater inflaterOne = LayoutInflater.from(parent.getContext());
            view = inflaterOne.inflate(R.layout.activity_show_books_entry, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) view.findViewById(R.id.Book_Image);
            viewHolder.text = (TextView) view.findViewById(R.id.Book_Text);
            view.setTag (viewHolder);

            viewHolder.image.setImageBitmap(bitmapList.get(index));
            viewHolder.text.setText(bookList.get(index).getText());
            view.setTag(viewHolder);

            return view;
        }
    }

    private class operationBI extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            bitmapList = new ArrayList<>();
            try {
                for(int j = 0; j <= bookList.size(); j++){
                    URL url = new URL(bookList.get(j).getImageURL());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(input);
                    bitmapList.add(bitmap);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            BooksAdapter adapter = new BooksAdapter (bookList);
            listView.setAdapter(adapter);
            Log.e("MESSAGE", "task finished!");
        }
    }
}