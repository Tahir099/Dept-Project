import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.borcdefteri.viewmodel.DetailPageViewModel
import com.example.borcdefteri.viewmodel.MainPageViewModel

class PersonDetailPageViewModelFactory(var application: Application) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailPageViewModel(application) as T
    }
}