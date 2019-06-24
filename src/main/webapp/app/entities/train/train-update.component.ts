import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITrain, Train } from 'app/shared/model/train.model';
import { TrainService } from './train.service';

@Component({
  selector: 'jhi-train-update',
  templateUrl: './train-update.component.html'
})
export class TrainUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    trainName: [],
    trainNumber: [],
    tFare: [],
    tTiming: []
  });

  constructor(protected trainService: TrainService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ train }) => {
      this.updateForm(train);
    });
  }

  updateForm(train: ITrain) {
    this.editForm.patchValue({
      id: train.id,
      trainName: train.trainName,
      trainNumber: train.trainNumber,
      tFare: train.tFare,
      tTiming: train.tTiming
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const train = this.createFromForm();
    if (train.id !== undefined) {
      this.subscribeToSaveResponse(this.trainService.update(train));
    } else {
      this.subscribeToSaveResponse(this.trainService.create(train));
    }
  }

  private createFromForm(): ITrain {
    return {
      ...new Train(),
      id: this.editForm.get(['id']).value,
      trainName: this.editForm.get(['trainName']).value,
      trainNumber: this.editForm.get(['trainNumber']).value,
      tFare: this.editForm.get(['tFare']).value,
      tTiming: this.editForm.get(['tTiming']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrain>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
