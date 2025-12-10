package com.application.mediumktor.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.application.mediumktor.ui.viewmodel.UsersUiState

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.application.mediumktor.data.Address
import com.application.mediumktor.data.Company
import com.application.mediumktor.data.UserDTOItem
import com.application.mediumktor.ui.viewmodel.UsersViewModel

@Composable
fun UsersRoute() {
    // This gets the ViewModel instance from ViewModelStore (or creates it)
    val viewModel: UsersViewModel = viewModel()

    UsersScreen(
        state = viewModel.uiState,
        onRetry = viewModel::loadUsers
    )
}


@Composable
fun UsersScreen(
    state: UsersUiState,
    onRetry: () -> Unit = {}
) {
    when {
        state.loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        state.error != null -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Error: ${state.error}")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onRetry) {
                    Text("Retry")
                }
            }
        }
        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize().padding(16.dp),
                        contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(state.users) { user ->
                    UserCard(user)
                }
            }
        }
    }
}

@Composable
fun UserCard(user: UserDTOItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Name: ${user.name}", style = MaterialTheme.typography.titleMedium)
            Text("Username: ${user.username}", style = MaterialTheme.typography.bodyMedium)
            Text("Email: ${user.email}", style = MaterialTheme.typography.bodyMedium)
            user.company?.let { Text("Company: ${it.name}", style = MaterialTheme.typography.bodySmall) }
            user.address?.let { Text("City: ${it.city}", style = MaterialTheme.typography.bodySmall) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UsersScreenPreview() {
    val sampleUsers = listOf(
        UserDTOItem(
            id = 1,
            name = "Leanne Graham",
            username = "Bret",
            email = "Sincere@april.biz",
            company = Company("Romaguera-Crona"),
            address = Address("Gwenborough")
        ),
        UserDTOItem(
            id = 2,
            name = "Ervin Howell",
            username = "Antonette",
            email = "Shanna@melissa.tv",
            company = Company("Deckow-Crist","test"),
            address = Address("Wisokyburgh")
        ),
        UserDTOItem(
            id = 3,
            name = "Clementine Bauch",
            username = "Samantha",
            email = "Nathan@yesenia.net",
            company = Company("Romaguera-Jacobson"),
            address = Address("McKenziehaven")
        )
    )

    UsersScreen(state = UsersUiState(users = sampleUsers))
}

