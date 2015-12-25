package explodeview.mraqfrod.com.myexplodeview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 *  自己学习用的第一个项目
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyExplosionFiled explosionField = new MyExplosionFiled(this);

        explosionField.addListener(findViewById(R.id.iv));
    }


}
