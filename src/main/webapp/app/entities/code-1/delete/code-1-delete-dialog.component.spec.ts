jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Code1Service } from '../service/code-1.service';

import { Code1DeleteDialogComponent } from './code-1-delete-dialog.component';

describe('Component Tests', () => {
  describe('Code1 Management Delete Component', () => {
    let comp: Code1DeleteDialogComponent;
    let fixture: ComponentFixture<Code1DeleteDialogComponent>;
    let service: Code1Service;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [Code1DeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(Code1DeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Code1DeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(Code1Service);
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
