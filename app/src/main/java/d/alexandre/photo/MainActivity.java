package d.alexandre.photo;

import android.util.Log;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Date;
import java.text.SimpleDateFormat;

import java.io.File;
import java.io.IOException;

import static java.lang.String.format;


public class MainActivity extends AppCompatActivity {
//constante de classe
    private static final int RETOUR_PRENDRE_PHOTO=1;
    //propriétés des object graphique
    private Button btnPrendrePhoto;
    private ImageView imgAffichePhoto;
    private String photoPath=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActivity();

        Log.d(" on create rentré du if ","lalalal");
    }
    //initialisation de l activity
    private void initActivity(){
        //récupération des objets graphique
        btnPrendrePhoto=(Button)findViewById(R.id.btnPrendrePhoto);
        imgAffichePhoto=(ImageView)findViewById(R.id.imgAffichePhoto);
        //méthode pour gérer les evénements
        createOnClicBtnPrendrePhoto();
    }
    //evenement clic sur bouton btn prendre photo
    private void createOnClicBtnPrendrePhoto(){
        btnPrendrePhoto.setOnClickListener(new Button.OnClickListener(){
            @Override
                    public void onClick(View v){
                      prendreUnePhoto();
            }
        });
    }
    // acces apareil photo et memorise dans fichier temporaire (usage de provider)
    private void prendreUnePhoto(){
        Log.d("lalalalalalalalalal prendreUnePhoto entré","lalala");
        //creer un intent pour ouvrir une fenetre pour prendre la photo
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //test pour controle que l'intent peut etre gérer ( pas de soucis avec l appareil photo)
        if(intent.resolveActivity(getPackageManager())!=null){
            Log.d("lalaa"," prendreUnePhoto rentré du if ");
            String time=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            Log.d("1","1");

            File photoDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            Log.d("2","2");
            try{
                Log.d("3","ENTRE DU TRY CATCH");
                File photoFile= File.createTempFile("photo"+time,".jpg",photoDir);
                Log.d("4","4");
                //enregistrer le chemin complet
                photoPath=photoFile.getAbsolutePath();
                Log.d("5",MainActivity.this.getApplicationContext().getPackageName());
                //creer l URI
                Uri photoUri = FileProvider.getUriForFile( this,
                        MainActivity.this.getApplicationContext().getPackageName()+".provider",
                photoFile);
                Log.d(" prendreUnePhoto rentré Uri ","llalalalaa");
                //transfert uri vers intent pour enregistrement photo dans fichier temporaire
                intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                //ouvrir l'activity par rapport à l'intent
                startActivityForResult(intent,RETOUR_PRENDRE_PHOTO);
                Log.d("lalal"," prendreUnePhoto rentré du start ");
            }catch(IOException e) {
                e.printStackTrace();
            }


        }
    }

    //evenementiel retour de l'appel de l'appareil photo(startActivityForResult
    /*
    * @param requestCode
    * @param resultCode
    * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        Log.d("mamam"," onActivityResult ");
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==RETOUR_PRENDRE_PHOTO&&resultCode==RESULT_OK){
            //récupérer l'image
            Log.d("nlal"," onActivityResult rentré du if ");
            Bitmap image= BitmapFactory.decodeFile(photoPath);
            //afficher l'image
            imgAffichePhoto.setImageBitmap(image);
            Log.d("saloe"," onActivityResult  fin du if ");
        }

    }
}
