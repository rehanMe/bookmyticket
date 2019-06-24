import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDestination, Destination } from 'app/shared/model/destination.model';
import { DestinationService } from './destination.service';

@Component({
  selector: 'jhi-destination-update',
  templateUrl: './destination-update.component.html'
})
export class DestinationUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    destinationName: []
  });

  constructor(protected destinationService: DestinationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ destination }) => {
      this.updateForm(destination);
    });
  }

  updateForm(destination: IDestination) {
    this.editForm.patchValue({
      id: destination.id,
      destinationName: destination.destinationName
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const destination = this.createFromForm();
    if (destination.id !== undefined) {
      this.subscribeToSaveResponse(this.destinationService.update(destination));
    } else {
      this.subscribeToSaveResponse(this.destinationService.create(destination));
    }
  }

  private createFromForm(): IDestination {
    return {
      ...new Destination(),
      id: this.editForm.get(['id']).value,
      destinationName: this.editForm.get(['destinationName']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDestination>>) {
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
