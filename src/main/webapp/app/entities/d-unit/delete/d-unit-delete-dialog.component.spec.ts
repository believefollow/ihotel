jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { DUnitService } from '../service/d-unit.service';

import { DUnitDeleteDialogComponent } from './d-unit-delete-dialog.component';

describe('Component Tests', () => {
  describe('DUnit Management Delete Component', () => {
    let comp: DUnitDeleteDialogComponent;
    let fixture: ComponentFixture<DUnitDeleteDialogComponent>;
    let service: DUnitService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DUnitDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(DUnitDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DUnitDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(DUnitService);
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
