jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ClassreportService } from '../service/classreport.service';
import { IClassreport, Classreport } from '../classreport.model';

import { ClassreportUpdateComponent } from './classreport-update.component';

describe('Component Tests', () => {
  describe('Classreport Management Update Component', () => {
    let comp: ClassreportUpdateComponent;
    let fixture: ComponentFixture<ClassreportUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let classreportService: ClassreportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ClassreportUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ClassreportUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClassreportUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      classreportService = TestBed.inject(ClassreportService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const classreport: IClassreport = { id: 456 };

        activatedRoute.data = of({ classreport });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(classreport));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const classreport = { id: 123 };
        spyOn(classreportService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ classreport });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: classreport }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(classreportService.update).toHaveBeenCalledWith(classreport);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const classreport = new Classreport();
        spyOn(classreportService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ classreport });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: classreport }));
        saveSubject.complete();

        // THEN
        expect(classreportService.create).toHaveBeenCalledWith(classreport);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const classreport = { id: 123 };
        spyOn(classreportService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ classreport });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(classreportService.update).toHaveBeenCalledWith(classreport);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
