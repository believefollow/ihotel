jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { CyRoomtypeService } from '../service/cy-roomtype.service';

import { CyRoomtypeDeleteDialogComponent } from './cy-roomtype-delete-dialog.component';

describe('Component Tests', () => {
  describe('CyRoomtype Management Delete Component', () => {
    let comp: CyRoomtypeDeleteDialogComponent;
    let fixture: ComponentFixture<CyRoomtypeDeleteDialogComponent>;
    let service: CyRoomtypeService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CyRoomtypeDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(CyRoomtypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CyRoomtypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(CyRoomtypeService);
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
