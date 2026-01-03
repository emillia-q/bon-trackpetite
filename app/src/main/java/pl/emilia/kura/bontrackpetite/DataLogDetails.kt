package pl.emilia.kura.bontrackpetite

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pl.emilia.kura.bontrackpetite.adapter.RowAdapter
import pl.emilia.kura.bontrackpetite.model.WeightData
import com.bumptech.glide.Glide

class DataLogDetails : AppCompatActivity() {
    lateinit var btnBack: Button
    lateinit var passedDate:TextView
    lateinit var recyclerView: RecyclerView
    lateinit var emptyListView: LinearLayout
    lateinit var ivsadCat: ImageView
    lateinit var progressBar: ProgressBar

    //firebase
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_data_log_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnBack=findViewById<Button>(R.id.btnBack)
        passedDate=findViewById<TextView>(R.id.passedDate)
        recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
        emptyListView=findViewById<LinearLayout>(R.id.emptyListView)
        ivsadCat=findViewById<ImageView>(R.id.ivsadCat)
        progressBar=findViewById<ProgressBar>(R.id.progressBar)

        //Create layout for recyclerView
        recyclerView.layoutManager= LinearLayoutManager(this)

        //pass the data from 1st activity
        val date=intent.getStringExtra("CHOSEN_DATE")

        passedDate.text=date

        //firebase
        firebaseDatabase= FirebaseDatabase.getInstance()
        databaseReference=firebaseDatabase.getReference("meals/${date}")

        retrieveDataFromFirebase()

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun retrieveDataFromFirebase(){
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list=mutableListOf<WeightData>()
                for(dataSnapshot in snapshot.children){
                    val data=dataSnapshot.getValue(WeightData::class.java)
                    data?.let{
                        list.add(it)
                    }
                }
                //The data are retrieved (or there are not any) --> we hide the progress bar
                progressBar.visibility=View.GONE
                if(list.isEmpty()){
                    recyclerView.visibility= View.GONE
                    emptyListView.visibility=View.VISIBLE

                    //Play cat GIF
                    Glide
                        .with(this@DataLogDetails)
                        .asGif()
                        .load(R.drawable.sad_cat)
                        .into(ivsadCat)
                }else {
                    emptyListView.visibility=View.GONE
                    recyclerView.visibility= View.VISIBLE

                    //Assign list to the adapter
                    recyclerView.adapter = RowAdapter(list)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DataLogDetails,"Failed to retrieve data: ${error.message}",
                    Toast.LENGTH_SHORT).show()
            }
        })
    }
}