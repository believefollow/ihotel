jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ClassreportRoomService } from '../service/classreport-room.service';

import { ClassreportRoomDeleteDialogComponent } from './classreport-room-delete-dialog.component';

describe('Component Tests', () => {
  describe('ClassreportRoom Management Delete Component', () => {
    let comp: ClassreportRoomDeleteDialogComponent;
    let fixture: ComponentFixture<ClassreportRoomDeleteDialogComponent>;
    let service: ClassreportRoomService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ClassreportRoomDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(ClassreportRoomDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClassreportRoomDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ClassreportRoomService);
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
