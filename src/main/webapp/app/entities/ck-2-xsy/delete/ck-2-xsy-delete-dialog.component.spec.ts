jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Ck2xsyService } from '../service/ck-2-xsy.service';

import { Ck2xsyDeleteDialogComponent } from './ck-2-xsy-delete-dialog.component';

describe('Component Tests', () => {
  describe('Ck2xsy Management Delete Component', () => {
    let comp: Ck2xsyDeleteDialogComponent;
    let fixture: ComponentFixture<Ck2xsyDeleteDialogComponent>;
    let service: Ck2xsyService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [Ck2xsyDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(Ck2xsyDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Ck2xsyDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(Ck2xsyService);
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
