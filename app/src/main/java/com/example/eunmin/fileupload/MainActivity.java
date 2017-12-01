package com.example.eunmin.fileupload;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

import javax.xml.transform.Result;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private Button button9;
    private Button button10;
    private int REQ_CODE = 22;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQ_CODE) {
            Uri uri = data.getData();
            String path = PathUtil.getPath(this, uri);
            file = new File(path);
            editText.setText(file.getName());
        }
    }

    private void initView() {
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("audio/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Audio "), REQ_CODE);
        });
        button9 = findViewById(R.id.button9);
        button10 = findViewById(R.id.button10);
        button10.setOnClickListener(v -> {
            // 만든 서비스 객체를 생성한다.
            FileUploadService service = RetrofitUtil.getInstance().create(FileUploadService.class);
            // 파일은 아까 위에서 인텐트로 가져온 파일을 이용
            // 업로드를 위한 세팅
            // "*/*"은 어떤걸 의미하는지 모르겠음
            RequestBody fileBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("userFile", file.getName(), fileBody);

            // 서버에 요청을한다.
            Call<Result> remote = service.test(body);

            // 서버에 요청한 결과를 받는다.
            remote.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    // 여기 코드는 알아서..
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    // 마찬가지로 알아서..
                }
            });
        });
    }
}

interface FileUploadService {
    // Multipart로 선언
    @Multipart
    // 포스트로 선언하고 (baUrl 주소 + /test)로 선언
    @POST("test")
    // 여기에서 Result는 전송한 후에 받아오는 객체 class
    // 그리고 전송할 떄 보낼 Multipart 객체를 보낸다고 선언
    Call<Result> test(@Part MultipartBody.Part fileBody);
}
