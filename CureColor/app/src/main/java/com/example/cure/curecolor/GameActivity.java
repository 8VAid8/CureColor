package com.example.cure.curecolor;


import java.util.ArrayList;

import ccdyngridview.DeleteZone;
import ccdyngridview.DragController;
import ccdyngridview.DynGridView;
import ccdyngridview.DynGridView.DynGridViewListener;
import ccdyngridview.DynGridViewAdapter;
import ccdyngridview.DynGridViewItemData;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

        import java.util.ArrayList;
import java.util.Arrays;

import ccdyngridview.DeleteZone;
        import ccdyngridview.DragController;
        import ccdyngridview.DynGridView;
        import ccdyngridview.DynGridViewAdapter;
        import ccdyngridview.DynGridViewItemData;

public class GameActivity extends Activity implements DynGridViewListener, View.OnClickListener {
    Paint p;
    Rect rect;
    TextView drag;
    Canvas canvas;

    MatrixGame gm;
    ArrayAdapter<String> adapter;
    TextView text;
    String color, game_type;
    Bitmap bitmap;
    Bitmap[] bitmaps;
    Integer fig_width;
    Integer fig_height;


    final static int		idTopLayout = Menu.FIRST + 100,
            idBack 		= Menu.FIRST + 101,
            idBotLayout	= Menu.FIRST + 102,
            idToggleScroll=Menu.FIRST+ 103,
            idToggleFavs = Menu.FIRST+ 104;

    DynGridViewAdapter	 	m_gridviewAdapter		= null;
    DeleteZone 				mDeleteZone				= null;
    ArrayList<DynGridViewItemData> itemList			= null;
    DynGridView 			gv						= null;
    boolean					mToggleScroll			= true,
            mToggleFavs				= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);

        gv = (DynGridView)findViewById(R.id.gridView);


        Bundle bundle = getIntent().getExtras();
        game_type = bundle.getString("type");
        color = bundle.getString("color");

        gm = new MatrixGame();
        gm.RandomMatrix();

        //заполнение GridView цветами в соответствии с gm.matrix
        Integer colors[] = ColorConverter.ToColorsMatrix(gm.matrix,color);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;

        CreateBitmaps(colors, gm.matrix, screenWidth, screenHeight);

        //---------
        //1. create gridview view


        //2. setup gridview data
        itemList = new ArrayList<DynGridViewItemData>();
        for (int i=0;i<bitmaps.length;i++) {
            DynGridViewItemData item = new DynGridViewItemData(
                    null, // item string name
                    fig_width, fig_height, 0, // sizes: item w, item h, item padding
                    bitmaps[i], // item background image
                    bitmaps[i],
                    bitmaps[i],
                    false, // favorite state, if favs are enabled
                    mToggleFavs, // favs disabled
                    bitmaps[i], // item image
                    i  // item id
            );

            //DynGridViewItemData item = new DynGridViewItemData("Item:"+i,
            //	R.drawable.itemback, R.drawable.itemimg,i);
            itemList.add(item);
        }

        //3. create adapter
        m_gridviewAdapter = new DynGridViewAdapter(this, itemList);

        //4. add adapter to gridview
        gv.setAdapter(m_gridviewAdapter);
        //gv.setColumnWidth(300);
        gv.setNumColumns(gm.columns);
        //gv.setSelection(2);
        gv.setDynGridViewListener((DynGridView.DynGridViewListener) this);




        // drag functionality
        gv.setDeleteView(mDeleteZone);
        DragController dragController = new DragController(this);

        gv.setDragController(dragController);

        // gv.getDragController().setDragEnabled(false); // disable DRAG functionality
        gv.setSwipeEnabled(mToggleScroll); // enable swipe but disable scrolling

    }

    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        int id = arg0.getId();
        // If cancel is pressed, close our app
        if (id == idBack) finish();
        if (id == idToggleScroll) {
            mToggleScroll = !mToggleScroll;
            gv.setSwipeEnabled(mToggleScroll);
            String text = "Swipe enabled:"+mToggleScroll;
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }
        if (id == idToggleFavs) {
            mToggleFavs = !mToggleFavs;
            for (DynGridViewItemData item:itemList)
                item.setFavoriteStateShow(mToggleFavs);
            m_gridviewAdapter.notifyDataSetChanged();
            gv.invalidateViews();

            String text = "Favs enabled:"+mToggleFavs;
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }
    }

    public void onItemClick(View v, int position, int id) {
      /*  String text = "Click on:"+id+ " " +
                ((DynGridViewItemData)m_gridviewAdapter.getItem(position)).getLabel();
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();*/
    }

    public void onItemFavClick(View v, int position, int id) {
        itemList.get(position).setFavoriteState(!itemList.get(position).getFavoriteState());
        m_gridviewAdapter.notifyDataSetChanged();
        gv.invalidateViews();

     /*   String text = "Item:"+position+ " fav state:" +
                ((DynGridViewItemData)m_gridviewAdapter.getItem(position)).getFavoriteState();
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();*/
    }

    public void onDragStart() {
    }

    public void onDragStop() {
    }

    public void onItemsChanged(int positionOne, int positionTwo) {
        String text = "You've changed item " + positionOne + " with item "+ positionTwo;
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

        //swap(int i1, int j1, int i2, int j2)
    }

    public void onItemDeleted(int position, int id) {
        String text = "You've deleted item " + id + " " +
                ((DynGridViewItemData)m_gridviewAdapter.getItem(position)).getLabel();
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void onSwipeLeft() {
        String text = "Swipe LEFT detected";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void onSwipeRight() {
        String text = "Swipe RIGHT detected";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void onSwipeUp() {
        String text = "Swipe UP detected";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void onSwipeDown() {
        String text = "Swipe DOWN detected";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }



    public void CreateBitmaps( Integer[] fill_colors, int[][] coords, Integer width, Integer height) {

        fig_width = width / coords[0].length;
        fig_height = height / coords.length;
        int[] colors = new int[fig_width * fig_height];

        bitmaps = new Bitmap[fig_width * fig_height];
        int pos = 0;


        for(int i = 0; i < coords.length; i++)
        {
            for(int j = 0; j < coords[0].length; j++)
            {
                Arrays.fill(colors, 0, fig_width * fig_height, fill_colors[coords[i][j]]);
                bitmaps[pos] = Bitmap.createBitmap(colors, fig_width, fig_height, Bitmap.Config.RGB_565);
                pos++;
                colors = new int[fig_width * fig_height];
            }
        }
    }

}
