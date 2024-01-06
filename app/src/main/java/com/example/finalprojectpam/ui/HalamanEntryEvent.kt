package com.example.finalprojectpam.ui

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalprojectpam.R
import com.example.finalprojectpam.model.DetailEvent
import com.example.finalprojectpam.model.EntryEventViewModel
import com.example.finalprojectpam.model.PenyediaViewModel
import com.example.finalprojectpam.model.UIStateEvent
import com.example.finalprojectpam.navigasi.DestinasiNavigasi
import com.example.finalprojectpam.navigasi.EventTopAppBar
import kotlinx.coroutines.launch
import java.util.Calendar

object DestinasiEntry : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = R.string.buatevent
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryEventScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EntryEventViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            EventTopAppBar(
                title = stringResource(DestinasiEntry.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerpadding ->
        EntryEventBody(
            uiStateEvent = viewModel.uiStateEvent,
            onEventValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveEvent()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerpadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EntryEventBody(
    uiStateEvent: UIStateEvent,
    onEventValueChange: (DetailEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        FormInputEvent(
            detailEvent = uiStateEvent.detailEvent,
            onValueChange = onEventValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = uiStateEvent.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.btn_submit))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FormInputEvent(
    detailEvent: DetailEvent,
    onValueChange: (DetailEvent) -> Unit = {},
    enabled: Boolean = true,
    modifier: Modifier
){

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(value = detailEvent.namaevent,
            onValueChange = {onValueChange(detailEvent.copy(namaevent = it))},
            label = { Text(stringResource(id = R.string.namaeventreq)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(value = detailEvent.alamatevent,
            onValueChange = {onValueChange(detailEvent.copy(alamatevent = it))},
            label = { Text(stringResource(id = R.string.alamatreq)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(value = detailEvent.penyelenggaraevent,
            onValueChange = {onValueChange(detailEvent.copy(penyelenggaraevent = it))},
            label = { Text(stringResource(id = R.string.penyelenggarareq)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(value = detailEvent.deskripsievent,
            onValueChange = {onValueChange(detailEvent.copy(deskripsievent = it))},
            label = { Text(stringResource(id = R.string.deskripsieventreq)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(value = detailEvent.waktuevent,
            onValueChange = {onValueChange(detailEvent.copy(waktuevent = it))},
            label = { Text(stringResource(id = R.string.waktueventreq)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailEvent.tanggalevent,
            onValueChange = { onValueChange(detailEvent.copy(tanggalevent = it)) },
            label = { Text(stringResource(id = R.string.tanggaleventreq)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
        )

        // Custom Date Picker Button
        DatePicker { selectedDate ->
            onValueChange(detailEvent.copy(tanggalevent = selectedDate))
        }
        if (enabled){
            Text(
                stringResource(id = R.string.required_field),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
        Divider(
            thickness = dimensionResource(id = R.dimen.padding_small),
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium))
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateButton(onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }
    var showDialog by remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = { showDialog = true },
        modifier = Modifier.size(48.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_date),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }

    LaunchedEffect(showDialog) {
        if (showDialog) {
            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, day ->
                    val selectedDate = "$day/$month/$year"
                    onDateSelected(selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePicker(onDateSelected: (String) -> Unit) {
    val context = LocalContext.current

    val calendar = remember { Calendar.getInstance() }
    val date = remember { mutableStateOf("") }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                date.value = "$day/$month/$year"
                onDateSelected(date.value)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }
    OutlinedButton(
        onClick = { datePickerDialog.show() },
        modifier = Modifier.size(48.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_date),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}