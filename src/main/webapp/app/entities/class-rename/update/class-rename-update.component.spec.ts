jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ClassRenameService } from '../service/class-rename.service';
import { IClassRename, ClassRename } from '../class-rename.model';

import { ClassRenameUpdateComponent } from './class-rename-update.component';

describe('Component Tests', () => {
  describe('ClassRename Management Update Component', () => {
    let comp: ClassRenameUpdateComponent;
    let fixture: ComponentFixture<ClassRenameUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let classRenameService: ClassRenameService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ClassRenameUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ClassRenameUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClassRenameUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      classRenameService = TestBed.inject(ClassRenameService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const classRename: IClassRename = { id: 456 };

        activatedRoute.data = of({ classRename });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(classRename));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const classRename = { id: 123 };
        spyOn(classRenameService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ classRename });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: classRename }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(classRenameService.update).toHaveBeenCalledWith(classRename);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const classRename = new ClassRename();
        spyOn(classRenameService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ classRename });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: classRename }));
        saveSubject.complete();

        // THEN
        expect(classRenameService.create).toHaveBeenCalledWith(classRename);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const classRename = { id: 123 };
        spyOn(classRenameService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ classRename });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(classRenameService.update).toHaveBeenCalledWith(classRename);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
