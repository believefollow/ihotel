jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { FwDsService } from '../service/fw-ds.service';

import { FwDsDeleteDialogComponent } from './fw-ds-delete-dialog.component';

describe('Component Tests', () => {
  describe('FwDs Management Delete Component', () => {
    let comp: FwDsDeleteDialogComponent;
    let fixture: ComponentFixture<FwDsDeleteDialogComponent>;
    let service: FwDsService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FwDsDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(FwDsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FwDsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(FwDsService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
