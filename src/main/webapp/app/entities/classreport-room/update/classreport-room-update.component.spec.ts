jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ClassreportRoomService } from '../service/classreport-room.service';
import { IClassreportRoom, ClassreportRoom } from '../classreport-room.model';

import { ClassreportRoomUpdateComponent } from './classreport-room-update.component';

describe('Component Tests', () => {
  describe('ClassreportRoom Management Update Component', () => {
    let comp: ClassreportRoomUpdateComponent;
    let fixture: ComponentFixture<ClassreportRoomUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let classreportRoomService: ClassreportRoomService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ClassreportRoomUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ClassreportRoomUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClassreportRoomUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      classreportRoomService = TestBed.inject(ClassreportRoomService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const classreportRoom: IClassreportRoom = { id: 456 };

        activatedRoute.data = of({ classreportRoom });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(classreportRoom));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const classreportRoom = { id: 123 };
        spyOn(classreportRoomService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ classreportRoom });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: classreportRoom }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(classreportRoomService.update).toHaveBeenCalledWith(classreportRoom);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const classreportRoom = new ClassreportRoom();
        spyOn(classreportRoomService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ classreportRoom });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: classreportRoom }));
        saveSubject.complete();

        // THEN
        expect(classreportRoomService.create).toHaveBeenCalledWith(classreportRoom);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const classreportRoom = { id: 123 };
        spyOn(classreportRoomService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ classreportRoom });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(classreportRoomService.update).toHaveBeenCalledWith(classreportRoom);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
